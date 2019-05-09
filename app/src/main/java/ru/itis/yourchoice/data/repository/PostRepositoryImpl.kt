package ru.itis.yourchoice.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import io.reactivex.Completable
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Interest
import ru.itis.yourchoice.core.model.Post
import javax.inject.Inject

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

    override fun addPostIntoDb(subcategory: Int?, postName: String, description: String): Completable {
        val postMap = HashMap<String, Any?>()
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

    override fun getPostsFromDb(interests: List<Interest>): Single<List<Post>> {
        return Single.fromCallable {
            val posts = mutableListOf<Post>()
            interests.forEach {
                Log.d("MYLOG", "getPostFromDb interest = ${it.subcategoryId}")
                posts.addAll(getPosts(it))
                Log.d("MYLOG", "after getting posts $posts")
            }

            posts
        }
    }

    private fun getPosts(interest: Interest): List<Post> {
        // шаг первый
        var posts = listOf<Post>()
        db.collection(POSTS)
                .whereEqualTo(SUBCATEGORY_ID, interest.subcategoryId)
                .get()
                .addOnSuccessListener { documents ->
                    // шаг третий (мапит данные)
                    posts = documents.map {
                        mapDocumentToPost(it)
                    }
                    Log.d("MYLOG", "mapped document = $posts ")

                }
                .addOnFailureListener { exception ->
                    Log.w("MYLOG", "Error getting documents: ", exception)
                }
        // шаг второй (возвращает пустой спиоок)
        return posts
    }

    private fun mapDocumentToPost(documentSnapshot: QueryDocumentSnapshot): Post =
            Post(
                    documentSnapshot.get(SUBCATEGORY_ID).toString().toInt(),
                    documentSnapshot.get(POST_NAME).toString(),
                    documentSnapshot.get(POST_DESCRIPTION).toString(),
                    documentSnapshot.get(OWNER_ID).toString(),
                    null,
                    null
            )

}
