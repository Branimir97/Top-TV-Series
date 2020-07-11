package branimirbutkovic.ferit.toptvseries.ui.activities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.room.SerieDatabase
import branimirbutkovic.ferit.toptvseries.models.Comment
import branimirbutkovic.ferit.toptvseries.sharedprefs.PreferenceManager
import branimirbutkovic.ferit.toptvseries.models.Serie
import branimirbutkovic.ferit.toptvseries.ui.adapters.comments.CommentsAdapter
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_serie_details.*

class SerieDetails : AppCompatActivity(), ValueEventListener {

    lateinit var dbRef: DatabaseReference
    var commentsAdapter: CommentsAdapter = CommentsAdapter()
    lateinit var name: String
    lateinit var imgUrl: String
    lateinit var overview: String
    var preferenceManager: PreferenceManager = PreferenceManager()
    val seriesDao = SerieDatabase.getInstance().serieDao()

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "branimirbutkovic.ferit.toptvseries.ui.activities"
    private val description = "Top TV series notification"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_details)

        setSeriesInfo()
        checkIsFavorite()
        ivFavorite.setOnClickListener{ setFavoriteSerie() }
        ivComment.setOnClickListener { isUserLoggedIn() }

        getDataFromDatabase()
        setRecycler()
    }

    private fun setSeriesInfo() {
        name = intent.getStringExtra("name")
        imgUrl = intent.getStringExtra("url")
        overview = intent.getStringExtra("overview")

        tvName.text = name

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w185" + imgUrl)
            .fit()
            .placeholder(R.drawable.ic_popular)
            .into(ivCover)

        tvOverview.text = overview
    }

    private fun checkIsFavorite() {
        if(isFavorite()) {
            ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_bordered)
        }
    }

    private fun isFavorite(): Boolean {
        val serie = seriesDao.getSerie(name)
        if(serie == null) {
            return false
        } else {
            return true
        }
    }

    private fun setFavoriteSerie() {
        if(isFavorite()) {
            ivFavorite.setImageResource(R.drawable.ic_favorite_bordered)
            seriesDao.deleteSerie(name)
            Toast.makeText(this, "Obrisali ste $name seriju iz omiljenih serija.", Toast.LENGTH_SHORT).show()
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            val serie = Serie(0, name, imgUrl, overview)
            seriesDao.insert(serie)
            Toast.makeText(this, "Serija $name označena kao omiljena serija.", Toast.LENGTH_SHORT).show()
            displayFavoriteSerieNotification()
        }
    }

    private fun displayFavoriteSerieNotification() {

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, SerieDetails::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle("Top TV series")
                .setContentText("Serija $name spremljena u omiljene serije!")
                .setSmallIcon(R.drawable.ic_favorite_filled)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher_icon3))
                .setContentIntent(pendingIntent)
        }

        notificationManager.notify(1, builder.build())
    }

    private fun getDataFromDatabase() {
        dbRef = FirebaseDatabase.getInstance().getReference("comments").child(name)

        dbRef.addValueEventListener(this)
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        commentsAdapter.clearComments()

        for(childSnap in snapshot.children){
            var description = childSnap.child("description").value.toString()
            var postedBy = childSnap.child("postedBy").value.toString()
            var one_comment = Comment(description = description, postedBy = postedBy)

            commentsAdapter.setComment(one_comment)
        }
    }

    private fun isUserLoggedIn() {
        if(preferenceManager.retrieveId()!="") {
            startAddCommentActivity()
        } else {
            startLoginActivity()
            Toast.makeText(this, "Za objavu komentara potrebna je prijava u sustav.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setRecycler() {
        rvComments.adapter = commentsAdapter
        rvComments.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvComments.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    private fun startLoginActivity() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        startActivity(loginActivity)
    }

    private fun startAddCommentActivity() {
        val addCommentActivity = Intent(this, AddCommentActivity::class.java)
        addCommentActivity.putExtra("name", name)
        startActivity(addCommentActivity)
    }
}