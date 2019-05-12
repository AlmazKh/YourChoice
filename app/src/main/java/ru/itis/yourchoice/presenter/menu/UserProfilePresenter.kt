package ru.itis.yourchoice.presenter.menu

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.UserProfileInteractor
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.menu.profile.UserProfileView

class UserProfilePresenter(
        private val userProfileInteractor: UserProfileInteractor
) : BasePresenter<UserProfileView>() {

    fun updateUserProfilePosts() =
            userProfileInteractor.getPosts()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view?.updateListView(it)
                    }, {
                        updatePostsFromCache()
                    })

    fun updatePostsFromCache() {

    }

    fun onPostClick(post: Post) = view?.navigateToDetails(post)
}