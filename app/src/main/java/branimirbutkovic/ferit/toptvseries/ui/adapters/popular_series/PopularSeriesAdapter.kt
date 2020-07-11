package branimirbutkovic.ferit.toptvseries.ui.adapters.popular_series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.models.Serie
import branimirbutkovic.ferit.toptvseries.ui.adapters.SerieInteractionListener

class PopularSeriesAdapter(serieInteractionListener: SerieInteractionListener) : RecyclerView.Adapter<PopularSeriesHolder>() {

    private val series = mutableListOf<Serie>()  //odmah se instancira
    private val serieInteractionListener: SerieInteractionListener = serieInteractionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularSeriesHolder {
        val serieView = LayoutInflater.from(parent.context).inflate(R.layout.item_serie, parent, false)
        val serieHolder =
            PopularSeriesHolder(
                serieView
            )
        return serieHolder
    }

    override fun getItemCount(): Int {
        return series.size
    }

    override fun onBindViewHolder(holder: PopularSeriesHolder, position: Int) {
        holder.bind(series[position], serieInteractionListener)
    }

    fun setSeries(series: List<Serie>) {
        this.series.clear()
        this.series.addAll(series)
        notifyDataSetChanged()
    }


}