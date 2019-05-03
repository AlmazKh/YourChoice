package ru.itis.yourchoice.view.menu.profile

import ru.itis.yourchoice.core.model.Post

interface UserProfileView {
    fun navigateToDetails(post: Post)
    fun updateListView(list: List<Post>)
}
