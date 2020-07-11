package branimirbutkovic.ferit.toptvseries.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import branimirbutkovic.ferit.toptvseries.*
import branimirbutkovic.ferit.toptvseries.networking.APIResponse
import branimirbutkovic.ferit.toptvseries.models.Serie
import branimirbutkovic.ferit.toptvseries.networking.NetworkUtils
import branimirbutkovic.ferit.toptvseries.ui.activities.SerieDetails
import branimirbutkovic.ferit.toptvseries.ui.adapters.SerieInteractionListener
import branimirbutkovic.ferit.toptvseries.ui.adapters.popular_series.PopularSeriesAdapter
import kotlinx.android.synthetic.main.fragment_popular_series.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularSeriesFragment : Fragment(), SerieInteractionListener{

    lateinit var topSeriesAdapter: PopularSeriesAdapter

    //dohvaćanje API servisa preko NetworkUtils klase
    private val seriesAPIService = NetworkUtils().getAPIService()

    //definiranje API-KEY-a
    private val api_key = "56f6273c9ebdfcfa244b52435680a1d3"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topSeriesAdapter = PopularSeriesAdapter(this)

        setRecycler()
        getPopularSeries(popularSeriesCallback())
    }

    override fun onShowDetails(serie: Serie) {
        val serieDetailsIntent = Intent(context, SerieDetails::class.java)
        serieDetailsIntent.putExtra("name", serie.name)
        serieDetailsIntent.putExtra("url", serie.imgUrl)
        serieDetailsIntent.putExtra("overview", serie.overview)
        startActivity(serieDetailsIntent)
    }

    private fun setRecycler() {
        rvTopSeries.adapter = topSeriesAdapter
        rvTopSeries.layoutManager = GridLayoutManager(context, 2)
    }

    //funkcija za definiranje requesta na API kako bi dobili response
    private fun getPopularSeries(popularSeriesCallback: Callback<APIResponse>) {
        seriesAPIService.getPopularSeries(api_key = api_key).enqueue(popularSeriesCallback)
    }

    //funkcija koja vraća response s API-ja
    private fun popularSeriesCallback() :Callback<APIResponse> = object: Callback<APIResponse>{

        override fun onFailure(call: Call<APIResponse>, t: Throwable) {
            Toast.makeText(context, "Provjeri Internetsku vezu", Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
            if(response.isSuccessful) {
                topSeriesAdapter.setSeries(response.body()!!.series)
            } else {
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}