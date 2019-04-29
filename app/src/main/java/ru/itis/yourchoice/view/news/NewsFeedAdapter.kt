package ru.itis.yourchoice.view.news

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.view.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.core.model.Post

class NewsFeedAdapter (
    private val newsLambda: (Post) -> Unit
) : ListAdapter<Post, NewsFeedAdapter.NewsFeedViewHolder>(NewsFeedDiffCallback()) {
    var list: MutableList<Post>? = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsFeedViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return NewsFeedViewHolder(inflater.inflate(R.layout.fragment_news, p0, false))
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.tvPostDescription.text = getItem(position).description
        holder.itemView.setOnClickListener {
            newsLambda.invoke(getItem(position))
        }
    }

    override fun submitList(list: MutableList<Post>?) {
        this.list = list
        super.submitList(list)
    }

    class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPostDescription = itemView.tv_post_description
    }

    class NewsFeedDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    }
}
