package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Interest
import ru.itis.yourchoice.core.model.Post

interface PostRepository {
    //    fun getPosts(categories: List<Category>): Single<MutableList<Post>>
    fun getPostsFromDb(interests: List<Interest>): Single<List<List<Post>>>
    fun addPostIntoDb(subcategory: Int?, postName: String, description: String): Completable
    fun updatePostsListWithUserName(posts: List<List<Post>>): Observable<List<Post>>
//    fun updatePostsListWithCategory(posts: List<Post>): Single<List<Post>>
}
