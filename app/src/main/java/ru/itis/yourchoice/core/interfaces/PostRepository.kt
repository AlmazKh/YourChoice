package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable

interface PostRepository {
    fun addPostIntoDb (category: Int, subcategory: String, description: String) : Completable
}
