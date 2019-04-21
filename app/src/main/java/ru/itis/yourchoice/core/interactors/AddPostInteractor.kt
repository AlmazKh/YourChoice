package ru.itis.yourchoice.core.interactors

import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.AddPostRepository
import ru.itis.yourchoice.core.model.Category
import java.lang.NumberFormatException
import javax.inject.Inject

//categories
private val CATEGORIES = arrayListOf("Films", "Series", "Events", "Books")

class AddPostInteractor @Inject constructor(
    private val addPostRepository: AddPostRepository
) {
    private var categoryId: Int = 0

    fun getMainCategories () : ArrayList<String> = CATEGORIES

    fun getCategories (category: Any?) : Maybe<MutableList<Category>> {
        categoryId = CATEGORIES.indexOf(category).toInt() + 1
             return addPostRepository.getCategories(categoryId)
                        .subscribeOn(Schedulers.io())
    }

    fun addPostIntoDb (subcategory: String, description: String) : Completable =
        addPostRepository.addPostIntoDb(categoryId, subcategory, description)
            .subscribeOn(Schedulers.io())
}
