package ru.itis.yourchoice.view.menu.profile

import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.User

interface UserProfileView {
    fun navigateToDetails(post: Post)
    fun updateListView(list: List<Post>)
    fun updateUserInfo(user: User)
}
