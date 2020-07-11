package branimirbutkovic.ferit.toptvseries.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.models.Comment
import branimirbutkovic.ferit.toptvseries.sharedprefs.PreferenceManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_comment.*

class AddCommentActivity : AppCompatActivity() {

    lateinit var name: String
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var comment: String
    lateinit var databaseUsers : DatabaseReference
    lateinit var databaseComments: DatabaseReference
    var preferenceManager: PreferenceManager = PreferenceManager()
    var count: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)

        name = intent.getStringExtra("name")

        getCurrentUserData()
        getNumberOfComments()
        btnSaveComment.setOnClickListener { saveComment() }
    }

    private fun getCurrentUserData() {

        val currentUser = preferenceManager.retrieveId()!!
        databaseUsers = FirebaseDatabase.getInstance().getReference("users").child(currentUser)

        databaseUsers.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                firstname = snapshot.child("firstName").value.toString()
                lastname = snapshot.child("lastName").value.toString()
            }
        })
    }

    private fun getNumberOfComments() {
        databaseComments = FirebaseDatabase.getInstance().getReference("comments").child(name)

        databaseComments.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                count = snapshot.childrenCount
            }
        })
    }

    private fun saveComment() {
        if(etComment.length() <= 0) {
            etComment.error = "Unesite komentar"
            Toast.makeText(this, "Potrebno je unijeti tekst u prostor za komentar.", Toast.LENGTH_SHORT).show()
        } else {
            storeCommentInDatabase()
        }
    }

    private fun storeCommentInDatabase() {
        comment = etComment.text.toString()
        val commentObject = Comment(firstname +  " " + lastname, comment)
        databaseComments.child(count.toString()).setValue(commentObject)
        Toast.makeText(this,"Komentar uspješno spremljen.", Toast.LENGTH_SHORT).show()
    }
}