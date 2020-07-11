package branimirbutkovic.ferit.toptvseries.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.sharedprefs.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    var preferenceManager: PreferenceManager = PreferenceManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener { loginUser() }
        tvRegister.setOnClickListener { startRegisterActivity() }
    }

    private fun startRegisterActivity() {
        val registerActivity = Intent(this, RegisterActivity::class.java)
        startActivity(registerActivity)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkParameters(): Boolean {

        if(etEmail.length()<=0 || etPassword.length()<=0 || !isEmailValid(etEmail.text.toString())) {

            if(etPassword.length()<=0) {
                etPassword.error = "Parametar nije unesen!"
            }
            if(!isEmailValid(etEmail.text.toString())) {
                etEmail.error = "Niste unijeli valjani oblik e-mail adrese!"
            }
            return false
        }
        else {
            return true
        }
    }

    private fun loginUser() {
        if(checkParameters()) {
            signIn()
        }
        else {
            Toast.makeText(this, "Provjerite parametre koji nisu uneseni.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn() {

        mAuth = FirebaseAuth.getInstance()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    preferenceManager.saveId(mAuth.currentUser!!.uid)
                    Toast.makeText(this, "Uspješno ste se prijavili, omogućeno Vam je komentiranje.", Toast.LENGTH_LONG).show()
                    startMainActivity()
                } else {
                    Toast.makeText(this, "Greška prilikom prijave.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun startMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }
}