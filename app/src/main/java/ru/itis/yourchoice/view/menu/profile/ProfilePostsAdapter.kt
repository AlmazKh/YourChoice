package ru.itis.yourchoice.view.menu.profile

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_news.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.core.model.Post

class ProfilePostsAdapter(
        private val postLambda: (Post) -> Unit
) : ListAdapter<Post, ProfilePostsAdapter.PostsViewHolder>(PostsDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return PostsViewHolder(inflater.inflate(R.layout.fragment_news, p0, false))
    }

    override fun onBindViewHolder(p0: PostsViewHolder, p1: Int) {
        p0.bind(getItem(p1))
        p0.itemView.setOnClickListener {
            postLambda.invoke(getItem(p1))
        }
    }

    class PostsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val transformation = RoundedCornersTransformation(20, 1)

        val requestOptions = RequestOptions()
                .centerCrop()
                .transforms(transformation)

        val thumbnail = Glide.with(containerView.context)
                .load(R.drawable.image_placeholder)
                .apply(requestOptions)

        fun bind(post: Post) {
            tv_post_name.text = post.postName
            tv_post_description.text = post.description
            tv_user_name.text = post.owner?.name
            tv_category_name.text = post.subcategory?.name
            Glide.with(containerView.context)
                    .load(post.owner?.photo)
                    .apply(requestOptions)
                    .thumbnail(thumbnail)
                    .into(iv_user_avatar)
        }
    }

    class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    }
}