package ru.itis.yourchoice.core.interactors

import android.util.Log
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.core.model.Post
import javax.inject.Inject

class NewsFeedInteractor
@Inject constructor(
    private val postRepository: PostRepository,
    private val usersRepository: UserRepository
) {
    fun getPostsFromDb (): Single<MutableList<Post>> {
        return usersRepository.getUsersInterests()
            .flatMap {
                Log.d("MYLOG", "NewsFeedInteractor flatMap ")
                postRepository.getPosts(it)
            }
            .subscribeOn(Schedulers.io())
    }
}