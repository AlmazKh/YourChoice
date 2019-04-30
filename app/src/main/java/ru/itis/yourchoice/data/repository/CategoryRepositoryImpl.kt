package ru.itis.yourchoice.data.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.model.Category
import javax.inject.Inject

private const val CATEGORIES = "categories"

private val films: Category = Category(0, "Films", 1)
private val series: Category = Category(0, "Series", 2)
private val events: Category = Category(0, "Events", 3)
private val books: Category = Category(0, "Books", 4)
private val categoriesList: List<Category> = arrayListOf(films, series, events, books)

class CategoryRepositoryImpl
@Inject constructor(
    private val db: FirebaseFirestore
): CategoryRepository {

    override fun getCategories(): Single<List<Category>> = Single.just(categoriesList)

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
