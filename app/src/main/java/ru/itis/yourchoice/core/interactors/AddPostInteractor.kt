package ru.itis.yourchoice.core.interactors

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Category
import javax.inject.Inject

//categories
private val CATEGORIES = arrayListOf("Films", "Series", "Events", "Books")

class AddPostInteractor @Inject constructor(
    private val postRepository: PostRepository,
    private val categoryRepository: CategoryRepository
) {
    fun getMainCategories () : ArrayList<String> = CATEGORIES

    fun getCategories (category: Any?) : Maybe<MutableList<Category>> =
        categoryRepository.getCategories(getMainCategoryId(category))
            .subscribeOn(Schedulers.io())

    fun addPostIntoDb (mainCategory: Any?, category: String, description: String) : Completable =
        postRepository.addPostIntoDb(
            getMainCategoryId(mainCategory),
            category,
            description
        ).subscribeOn(Schedulers.io())

    private fun getMainCategoryId (category: Any?): Int = CATEGORIES.indexOf(category) + 1

}
