package ru.itis.yourchoice.view.menu.likes

import ru.itis.yourchoice.core.model.Post

interface LikesView {
    fun navigateToDetails(post: Post)
    fun updateListView(list: List<Post>)
}