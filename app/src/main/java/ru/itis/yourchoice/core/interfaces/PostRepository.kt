package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Post

interface PostRepository {
    fun getPosts(categories: List<Category>): Single<MutableList<Post>>
    fun addPostIntoDb (subcategory: Int, postName: String, description: String) : Completable
}
