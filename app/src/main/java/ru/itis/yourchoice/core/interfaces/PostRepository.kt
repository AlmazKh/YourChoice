package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Post

interface PostRepository {
    fun addPostIntoDb (category: Int, subcategory: String, postName: String, description: String) : Completable
    fun getCurrentUserPosts(): Single<List<Post>>
}
