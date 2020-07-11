package branimirbutkovic.ferit.toptvseries.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var firebaseUserID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener { registerUser() }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkParameters(): Boolean {

        if(etFirstName.length()<=0 || etLastName.length()<=0 || etEmail.length()<=0 || !isEmailValid(etEmail.text.toString()) || etPassword.length()<8) {
            if(etFirstName.length()<=0) {
                etFirstName.error = "Parametar nije unesen."
            }
            if(etLastName.length()<=0) {
                etLastName.error = "Parametar nije unesen."
            }
            if(!isEmailValid(etEmail.text.toString())) {
                etEmail.error = "Unesite valjani oblik e-mail adrese."
            }
            if(etPassword.length()<8) {
                etPassword.error = "Lozinka mora sadržavati min. 8 znakova."
            }
            return false
        }
        else {
            return true
        }
    }

    private fun registerUser() {
        if(checkParameters()) {
            signUp()
        }
        else {
            Toast.makeText(this, "Provjerite parametre koji nisu uneseni.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUp() {

        mAuth = FirebaseAuth.getInstance()
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        val user = User(
            firstName,
            lastName,
            email
        )

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    //registracija u Authentification uspješna, dohvat ID-a za spremanje u RT bazu
                    firebaseUserID = mAuth.currentUser!!.uid

                    //dohvat reference "users", ukoliko ne postoji, kreira se sama
                    database = FirebaseDatabase.getInstance().getReference("users")

                    //postavljanje ID-a za dijete i pohrana podataka o User-u i data klase
                    database
                        .child(firebaseUserID)
                        .setValue(user)

                    Toast.makeText(this, "Uspješno ste se registrirali!", Toast.LENGTH_SHORT).show()
                    startLoginActivity()

                } else {
                    Toast.makeText(this, "Pogreška prilikom registracije.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startLoginActivity() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        startActivity(loginActivity)
    }
}