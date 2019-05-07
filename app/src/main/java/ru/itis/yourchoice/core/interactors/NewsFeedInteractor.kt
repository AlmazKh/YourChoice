package ru.itis.yourchoice.core.interactors

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.InterestRepository
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.User
import javax.inject.Inject

class NewsFeedInteractor
@Inject constructor(
        private val postRepository: PostRepository,
        private val interestRepository: InterestRepository,
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository
) {
    fun getPostsFromDb(): Single<List<Post>> =
            interestRepository.getUsersInterests()
                    .flatMap {
                        Log.d("MYLOG", "NewsFeedInteractor flatMap ")
                        postRepository.getPostsFromDb(it)
                    }
                    .flatMap{
                        Log.d("MYLOG3", "$it ")
                        postRepository.updatePostsListWithUserName(it)
                    }
                   .flatMap {
                       categoryRepository.updatePostsListWithCategory(it)
                   }
                    .subscribeOn(Schedulers.io())

    fun getUserById(id: String): Single<User> =
            userRepository.getUserFromDbById(id)
                    .subscribeOn(Schedulers.io())
}