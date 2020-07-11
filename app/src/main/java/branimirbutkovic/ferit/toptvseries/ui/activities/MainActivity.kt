package branimirbutkovic.ferit.toptvseries.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.sharedprefs.PreferenceManager
import branimirbutkovic.ferit.toptvseries.ui.fragments.FavoriteSeriesFragment
import branimirbutkovic.ferit.toptvseries.ui.fragments.PopularSeriesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    var preferenceManager: PreferenceManager = PreferenceManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popularSeriesFragment = PopularSeriesFragment()
        val favoriteSeriesFragment = FavoriteSeriesFragment()

        //postavljanje inicijalno aktivnog fragmenta
        setFragment(popularSeriesFragment)

        //postavljanje osluškivanja na bottomNavigation te zamjena fragmenata ovisno koji je kliknut
        bottom_navigation.setOnNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.item_top -> setFragment(popularSeriesFragment)
                R.id.item_favorite -> setFragment(favoriteSeriesFragment)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(getUserId() == "") {
            return false
        } else {
            menuInflater.inflate(R.menu.logout_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_logout) {
            signout()
            item.setVisible(false)
            Toast.makeText(this, "Uspješno ste se odjavili.", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserId(): String? {
        return preferenceManager.retrieveId()
    }

    private fun signout() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        preferenceManager.saveId("")
    }

    //funkcija koja omogućuje prebacivanje fragmenata
    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }
}