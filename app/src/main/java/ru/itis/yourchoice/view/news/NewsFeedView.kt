package ru.itis.yourchoice.view.news

import ru.itis.yourchoice.core.model.Post

interface NewsFeedView {
    fun updateListView(list: List<Post>)
    fun showProgress()
    fun hideProgress()
    fun navigateToDetails(post: Post)
}