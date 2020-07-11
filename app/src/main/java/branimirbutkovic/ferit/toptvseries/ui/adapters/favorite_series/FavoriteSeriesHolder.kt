package branimirbutkovic.ferit.toptvseries.ui.adapters.favorite_series

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.models.Serie
import branimirbutkovic.ferit.toptvseries.ui.adapters.SerieInteractionListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_serie.view.*

class FavoriteSeriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(serie: Serie, serieInteractionListener: SerieInteractionListener) {
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w185" + serie.imgUrl)
            .fit()
            .placeholder(R.drawable.ic_favorite)
            .error(android.R.drawable.stat_notify_error)
            .into(itemView.ivCover)

        itemView.setOnClickListener{(serieInteractionListener.onShowDetails(serie))}
    }
}