package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable

interface PostRepository {
    fun addPostIntoDb (category: Int, subcategory: String, postName: String, description: String) : Completable
}
