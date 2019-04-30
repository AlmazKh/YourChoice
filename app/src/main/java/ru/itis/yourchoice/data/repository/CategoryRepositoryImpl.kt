package ru.itis.yourchoice.data.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.data.CategoriesHolder
import javax.inject.Inject

private const val CATEGORIES = "categories"

class CategoryRepositoryImpl
@Inject constructor(
    private val db: FirebaseFirestore,
    private val categoriesHolder: CategoriesHolder
): CategoryRepository {

    override fun getCategories(): Single<List<Category>> = Single.just(categoriesHolder.getCategories())

    override fun getSubcategories(category: Int): Maybe<List<Category>> {
        return Maybe.create { emitter ->
            db.collection(CATEGORIES)
                .whereEqualTo("category_id", category)
                .get()
                .addOnSuccessListener { documents ->
                    val list: ArrayList<Category> = ArrayList()
                    for (document in documents) {
                        list.add(document.toObject(Category::class.java))
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }
        }
    }
}
