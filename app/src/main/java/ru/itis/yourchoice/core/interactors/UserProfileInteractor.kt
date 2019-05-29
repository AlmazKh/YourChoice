package ru.itis.yourchoice.core.interactors

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.User
import javax.inject.Inject

class UserProfileInteractor
@Inject constructor(
        private val postRepository: PostRepository,
        private val categoryRepository: CategoryRepository,
        private val userRepository: UserRepository
) {

    fun getPosts(): Single<List<Post>> =
            postRepository.getCurrentUserPosts()
                    .flatMap {
                        userRepository.updatePostsListWithUserName(it)
                    }
                    .flatMap {
                        categoryRepository.updatePostsListWithCategory(it)
                    }
                    .subscribeOn(Schedulers.io())

    fun getUserInfo(): Single<User> =
            userRepository.getCurrentUser()
                    .subscribeOn(Schedulers.io())
}