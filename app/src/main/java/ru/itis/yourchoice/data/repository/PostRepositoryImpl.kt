package ru.itis.yourchoice.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Post
import javax.inject.Inject

private const val CATEGORY_ID = "category_id"
private const val SUBCATEGORY_ID = "subcategory_id"
private const val POST_NAME = "name"
private const val POST_DESCRIPTION = "description"
private const val OWNER_ID = "owner_id"
private const val POSTS = "posts"
/*  CATEGORY_ID can be:
    1 = films
    2 = series
    3 = events
    4 = books
    */

class PostRepositoryImpl
@Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val db: FirebaseFirestore
) : PostRepository {

    override fun addPostIntoDb(category: Int, subcategory: String, postName: String, description: String): Completable {
        val postMap = HashMap<String, Any?>()
        postMap[CATEGORY_ID] = category
        postMap[SUBCATEGORY_ID] = subcategory
        postMap[POST_NAME] = postName
        postMap[POST_DESCRIPTION] = description
        postMap[OWNER_ID] = firebaseAuth.currentUser?.uid
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

    override fun getCurrentUserPosts(): Single<List<Post>> {
        return Single.create { emitter ->
            db.collection(POSTS)
                    .whereEqualTo(OWNER_ID, firebaseAuth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        var list: ArrayList<Post> = ArrayList()
                        for (document in documents) {
                            list.add(document.toObject(Post::class.java))
                            Log.d("MYLOG", "PostRepo getCurrentUserPosts ${document.id} => ${document.data}")
                            Log.d("MYLOG", "list[0] = ${list[0]}")
                        }
                        emitter.onSuccess(list)
                    }
                    .addOnFailureListener { exception ->
                        Log.w("MYLOG", "Error getting documents: ", exception)
                        emitter.onError(exception)
                    }
        }
    }
}
