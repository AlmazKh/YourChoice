package ru.itis.yourchoice.presenter.menu

import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.menu.likes.LikesView
import javax.inject.Inject

class LikesPresenter
@Inject constructor(): BasePresenter<LikesView>() {
    fun updateUserProfilePosts() {

    }

    fun updatePostsFromCache() {

    }

    fun onPostClick(post: Post) = view?.navigateToDetails(post)

}