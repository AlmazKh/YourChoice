package ru.itis.yourchoice.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import ru.itis.yourchoice.core.interfaces.PostRepository
import javax.inject.Inject

private const val OWNER_ID = "owner_id"
private const val MAIN_CATEGORY_ID = "main_category"
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

class PostRepositoryImpl
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): PostRepository {
    override fun addPostIntoDb(category: Int, subcategory: String, description: String) : Completable {
        val postMap = HashMap<String, Any?>()
        postMap[OWNER_ID] = firebaseAuth.currentUser?.uid
        postMap[MAIN_CATEGORY_ID] = category
        postMap[CATEGOGY_NAME] = subcategory
        postMap[POST_DESCRIPTION] = description
        return Completable.create { emitter ->
            db.collection(POSTS)
                .add(postMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Exception(""))
                    }
                }
        }
    }
}
