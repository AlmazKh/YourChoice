package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Interest
import ru.itis.yourchoice.core.model.Post

interface PostRepository {
    fun getPostsFromDb(interests: List<Interest>): Single<List<List<Post>>>
    fun addPostIntoDb(subcategory: Int?, postName: String, description: String): Completable
    fun getCurrentUserPosts(): Single<List<Post>>
}
