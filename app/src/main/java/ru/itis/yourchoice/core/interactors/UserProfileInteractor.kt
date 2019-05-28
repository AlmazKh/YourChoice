package ru.itis.yourchoice.core.interactors

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Post
import javax.inject.Inject

class UserProfileInteractor
@Inject constructor(
        private val postRepository: PostRepository
) {

    fun getPosts(): Single<List<Post>> =
            postRepository.getCurrentUserPosts()
                .subscribeOn(Schedulers.io())
}