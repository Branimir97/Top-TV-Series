package branimirbutkovic.ferit.toptvseries.ui.adapters.favorite_series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.models.Serie
import branimirbutkovic.ferit.toptvseries.ui.adapters.SerieInteractionListener

class FavoriteSeriesAdapter(serieInteractionListener: SerieInteractionListener) : RecyclerView.Adapter<FavoriteSeriesHolder> (){

    val favorite_series = mutableListOf<Serie>()
    private val serieInteractionListener: SerieInteractionListener = serieInteractionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteSeriesHolder {
        val serieView = LayoutInflater.from(parent.context).inflate(R.layout.item_serie, parent, false)
        val favoriteSeriesHolder =
            FavoriteSeriesHolder(
                serieView
            )

        return favoriteSeriesHolder
    }

    override fun getItemCount(): Int {
        return favorite_series.size
    }

    override fun onBindViewHolder(holder: FavoriteSeriesHolder, position: Int) {
        holder.bind(favorite_series[position], serieInteractionListener)
    }

    fun setSeries(series: List<Serie>) {
        favorite_series.clear()
        favorite_series.addAll(series)
        notifyDataSetChanged()
    }
}