package ru.itis.yourchoice.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.model.Interest
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.Subcategory
import ru.itis.yourchoice.core.model.User
import javax.inject.Inject

private const val SUBCATEGORY_ID = "subcategory_id"
private const val POST_NAME = "name"
private const val POST_DESCRIPTION = "description"
private const val OWNER_ID = "owner_id"
private const val POSTS = "posts"
private const val USERS = "users"
private const val USER_ID = "user_id"
private const val USER_NAME = "name"

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

    //    override fun getPostsFromDb(interests: List<Interest>): Single<List<Post>> {
//        return Single.create { emitter ->
//            Log.d("MYLOG", "PostRepo getPostsFromDb 1")
//            var list: ArrayList<Post> = ArrayList()
//            for (interest in interests) {
//                db.collection(POSTS)
//                    .whereEqualTo("subcategory_id", interest.subcategoryId)
//                    .get()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            var post = Post.Builder()
//                                .subcategory(
//                                    Subcategory.Builder()
//                                    .id(document.get(SUBCATEGORY_ID).toString().toInt())
//                                    .build()
//                                )
//                                .postName(document.get(POST_NAME).toString())
//                                .description(document.get(POST_DESCRIPTION).toString())
//                                .owner(
//                                    User.Builder()
//                                        .id(document.get(OWNER_ID).toString())
//                                        .build()
//                                )
//                                .build()
//
//                            list.add(post)
//
//                            Log.d("MYLOG", "PostRepo getPostsFromDb 2 ${document.id} => ${document.data}")
//                            Log.d("MYLOG", "list[0] = ${list[0]}")
//                        }
//                        emitter.onSuccess(list)
//
//                    }
//                    .addOnFailureListener { exception ->
//                        Log.w("MYLOG", "Error getting documents: ", exception)
//                        emitter.onError(exception)
//                    }
//            }
//
//        }
//    }
    override fun getPostsFromDb(interests: List<Interest>): Single<List<List<Post>>> {
        return Observable.fromIterable(interests)
                .flatMapSingle {
                    getPost(it)
                }
                .concatMap {
                    Observable.just(it)
                }
                .toList()
    }

    fun getPost(interest: Interest): Single<List<Post>> =
            Single.create { emitter ->
                var list: ArrayList<Post> = ArrayList()

                db.collection(POSTS)
                        .whereEqualTo("subcategory_id", interest.subcategoryId)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                var post = Post.Builder()
                                        .subcategory(
                                                Subcategory.Builder()
                                                        .id(document.get(SUBCATEGORY_ID).toString().toInt())
                                                        .build()
                                        )
                                        .postName(document.get(POST_NAME).toString())
                                        .description(document.get(POST_DESCRIPTION).toString())
                                        .owner(
                                                User.Builder()
                                                        .id(document.get(OWNER_ID).toString())
                                                        .build()
                                        )
                                        .build()

                                list.add(post)

                                Log.d("MYLOG", "PostRepo getPostsFromDb 2 ${document.id} => ${document.data}")
                                Log.d("MYLOG", "list[0] = ${list[0]}")
                            }
                            emitter.onSuccess(list)

                        }
                        .addOnFailureListener { exception ->
                            Log.w("MYLOG", "Error getting documents: ", exception)
                            emitter.onError(exception)
                        }
            }

    override fun updatePostsListWithUserName(posts: List<List<Post>>): Single<List<Post>> {
        return Single.create { emitter ->
            val postsList: MutableList<Post> = mutableListOf()
            val updatedPosts: ArrayList<Post> = ArrayList()
            for (post in posts) {
                postsList.addAll(post)
            }
            for (post in postsList) {
                db.collection(USERS)
                        .whereEqualTo(USER_ID, post.owner?.id)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                post.owner?.name = document.get(USER_NAME).toString()
                                updatedPosts.add(post)
                                Log.d("MYLOG2", "post updated = $post")
                                Log.d("MYLOG2", "list[0] updated = ${updatedPosts[0]}")
                            }
                            emitter.onSuccess(updatedPosts)
                        }
                        .addOnFailureListener { exception ->
                            emitter.onError(exception)
                        }
            }
        }
    }
}
