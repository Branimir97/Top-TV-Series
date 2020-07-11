package branimirbutkovic.ferit.toptvseries.ui.adapters.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import branimirbutkovic.ferit.toptvseries.R
import branimirbutkovic.ferit.toptvseries.models.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsHolder>() {

    private val comments = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsHolder {
        val commentView = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false )

        return CommentsHolder(
            commentView
        )
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentsHolder, position: Int) {
        holder.bind(comments[position])
    }

    fun setComment(comment: Comment) {
        this.comments.add(comment)
        notifyDataSetChanged()
    }

    fun clearComments() {
        this.comments.clear()
        notifyDataSetChanged()
    }
}