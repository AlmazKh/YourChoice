package ru.itis.yourchoice.core.interactors

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Subcategory
import javax.inject.Inject

class AddPostInteractor
@Inject constructor(
        private val postRepository: PostRepository,
        private val categoryRepository: CategoryRepository
) {

    fun getCategories(): Single<List<Category>> =
        categoryRepository.getCategories()
            .subscribeOn(Schedulers.io())

    fun getSubcategories(category: String): Single<List<Subcategory>> =
        categoryRepository.getCategoryIdByName(category)
            .flatMap {
                categoryRepository.getSubcategories(it.id)
            }
            .subscribeOn(Schedulers.io())

    fun addPostIntoDb(subcategory: String, postName: String, description: String): Completable =
        categoryRepository.getSubategoryIdByName(subcategory)
            .flatMapCompletable {
                postRepository.addPostIntoDb(
                    it.id,
                    postName,
                    description
                )
            }
            .subscribeOn(Schedulers.io())
}
