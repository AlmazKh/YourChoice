package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable

interface PostRepository {
    fun addPostIntoDb (subcategory: Int, postName: String, description: String) : Completable
}
