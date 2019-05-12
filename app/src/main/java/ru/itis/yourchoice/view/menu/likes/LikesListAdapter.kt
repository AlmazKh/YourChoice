package ru.itis.yourchoice.view.menu.likes

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.itis.yourchoice.R
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.view.menu.profile.ProfilePostsAdapter

class LikesListAdapter(
    private val postLambda: (Post) -> Unit
): ListAdapter<Post, LikesListAdapter.PostsViewHolder>(PostsDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return PostsViewHolder(inflater.inflate(R.layout.fragment_user_profile, p0, false))
    }

    override fun onBindViewHolder(p0: PostsViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class PostsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            //fields
        }
    }

    class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    }
}