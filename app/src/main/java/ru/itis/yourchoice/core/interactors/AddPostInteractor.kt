package ru.itis.yourchoice.core.interactors

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Category
import javax.inject.Inject

//categories
private val CATEGORIES = arrayListOf("Films", "Series", "Events", "Books")

class AddPostInteractor
@Inject constructor(
        private val postRepository: PostRepository,
        private val categoryRepository: CategoryRepository
) {
    fun getMainCategories(): Single<List<Category>> =
        categoryRepository.getCategories()
            .subscribeOn(Schedulers.io())

    fun getSubcategories(category: Any?): Maybe<List<Category>> =
            categoryRepository.getSubcategories(getMainCategoryId(category))
                    .subscribeOn(Schedulers.io())

    fun addPostIntoDb(category: Any?, subcategory: String, postName: String, description: String): Completable =
            postRepository.addPostIntoDb(
                    getMainCategoryId(category),
                    subcategory,
                    postName,
                    description
            ).subscribeOn(Schedulers.io())

    private fun getMainCategoryId(category: Any?): Int = CATEGORIES.indexOf(category) + 1

}
