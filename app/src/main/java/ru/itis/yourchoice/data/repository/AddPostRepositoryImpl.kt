package ru.itis.yourchoice.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Maybe
import ru.itis.yourchoice.core.interfaces.AddPostRepository
import ru.itis.yourchoice.core.model.Category
import javax.inject.Inject

private const val OWNER_ID = "owner_id"
private const val MAIN_CATEGORY_ID = "id"
private const val CATEGOGY_NAME = "name"
private const val POST_DESCRIPTION = "description"
private const val POSTS = "posts"
/*  CATEGORY_ID can be:
    0 = series, films, books
    1 = films
    2 = series
    3 = events
    4 = books
    */

class AddPostRepositoryImpl
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AddPostRepository {
    override fun getCategories(category: Int): Maybe<MutableList<Category>> {
        return Maybe.create { emitter ->
            db.collection("categories")
                .whereEqualTo("id", category)
                .get()
                .addOnSuccessListener { documents ->
                    val list: ArrayList<Category> = ArrayList()
                    for (document in documents) {
//                        list.put(document.data["name"].toString())
                        list.add(document.toObject(Category::class.java))
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }

    override fun addPostIntoDb(category: Int, subcategory: String, description: String) {
        val postMap = HashMap<String, Any?>()
        postMap[OWNER_ID] = firebaseAuth.currentUser?.uid
        postMap[MAIN_CATEGORY_ID] = category
        postMap[CATEGOGY_NAME] = subcategory
        postMap[POST_DESCRIPTION] = description
        db.collection(POSTS)
            .add(postMap)
            .addOnSuccessListener {
                Log.d("MYLOG", it.toString())
            }.addOnFailureListener {
                Log.d("MYLOG", it.message)
            }
    }
}