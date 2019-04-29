package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Post

interface PostRepository {
    fun addPostIntoDb (category: Int, subcategory: String, postName: String, description: String) : Completable
//    fun getPostsFromDb(categories: List<Category>): Maybe<MutableList<Post>>
    fun getPosts(categories: List<Category>): Single<MutableList<Post>>
}
