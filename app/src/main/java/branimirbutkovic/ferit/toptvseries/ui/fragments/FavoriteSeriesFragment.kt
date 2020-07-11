package branimirbutkovic.ferit.toptvseries.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import branimirbutkovic.ferit.toptvseries.ui.adapters.favorite_series.FavoriteSeriesAdapter
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.room.SerieDatabase
import branimirbutkovic.ferit.toptvseries.models.Serie
import branimirbutkovic.ferit.toptvseries.ui.activities.SerieDetails
import branimirbutkovic.ferit.toptvseries.ui.adapters.SerieInteractionListener
import kotlinx.android.synthetic.main.fragment_favorite_series.*

class FavoriteSeriesFragment : Fragment(), SerieInteractionListener {

    private lateinit var favoriteSeriesAdapter: FavoriteSeriesAdapter
    var seriesDao = SerieDatabase.getInstance().serieDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_series, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteSeriesAdapter = FavoriteSeriesAdapter(this)
        displayData()
    }

    private fun displayData() {
        rvFavoriteSeries.adapter = favoriteSeriesAdapter
        favoriteSeriesAdapter.setSeries(seriesDao.getAll())
        rvFavoriteSeries.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onShowDetails(serie: Serie) {
        val serieDetailsIntent = Intent(context, SerieDetails::class.java)
        serieDetailsIntent.putExtra("name", serie.name)
        serieDetailsIntent.putExtra("url", serie.imgUrl)
        serieDetailsIntent.putExtra("overview", serie.overview)
        startActivity(serieDetailsIntent)
    }
}