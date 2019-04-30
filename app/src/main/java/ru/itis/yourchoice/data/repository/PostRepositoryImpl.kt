package ru.itis.yourchoice.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Post
import javax.inject.Inject

private const val CATEGORY_ID = "category_id"
private const val SUBCATEGORY_NAME = "subcategory_id"
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
        postMap[SUBCATEGORY_NAME] = subcategory
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

    override fun getPosts(categories: List<Category>): Single<MutableList<Post>> {
        return Observable.fromIterable(categories)
            .flatMap {
                Log.d("MYLOG", "getPost repo flatMap, category: $it")
                getPostsFromDb(it)
            }
            .concatMap {
                Log.d("MYLOG", "getPost repo concat, category: $it")
                Observable.just(it)
            }
            .toList()
    }

    fun getPostsFromDb(category: Category): Observable<Post> {
        return Observable.create {
            Log.d("MYLOG", "PostRepo getPostsFromDb 1 $it")
            db.collection(POSTS)
                .whereEqualTo(CATEGORY_ID, category.categoryId)
                .whereEqualTo(SUBCATEGORY_NAME, category.subcategoryName)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("MYLOG", "PostRepo getPostsFromDb 2 ${document.id} => ${document.data}")
                    }

                }
                .addOnFailureListener { exception ->
                    Log.w("MYLOG", "Error getting documents: ", exception)
                }
            Log.d("MYLOG", "PostRepo getPostsFromDb 3 $it")
        }
    }
//        Maybe.create { emitter ->
//            db.collection("POSTS")
////                .whereEqualTo(MAIN_CATEGORY_ID, categories.get(0).id)
////                .whereEqualTo(CATEGOGY_NAME, categories.get(0).subcategoryName)
//                .get()
//                .addOnSuccessListener { documents ->
//                    val list: ArrayList<Post> = ArrayList()
//                    for (document in documents) {
//                        list.add(document.toObject(Post::class.java))
//                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                    }
//                    emitter.onSuccess(list)
//                }
//                .addOnFailureListener { exception ->
//                    emitter.onError(exception)
//                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
//                }
//        }
//    }

//    override fun getPostsFromDb(categories: List<Category>): Maybe<MutableList<Post>> {
//        val list: ArrayList<Post> = ArrayList()
//        return Maybe.create { emitter ->
//            db.collection("POSTS")
//                .whereEqualTo(CATEGORY_ID, categories.get(0).categoryId)
//                .whereEqualTo(SUBCATEGORY_NAME, categories.get(0).subcategoryName)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        list.add(document.toObject(Post::class.java))
//                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                    }
//                    emitter.onSuccess(list)
//                }
//                .addOnFailureListener { exception ->
//                    emitter.onError(exception)
//                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
//                }
//        }
//    }
}
