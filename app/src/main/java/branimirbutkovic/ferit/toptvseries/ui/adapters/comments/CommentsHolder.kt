package branimirbutkovic.ferit.toptvseries.ui.adapters.comments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import branimirbutkovic.ferit.toptvseries.models.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: Comment) {
        itemView.tvPostedBy.text = comment.postedBy
        itemView.tvDescription.text = comment.description
    }
}