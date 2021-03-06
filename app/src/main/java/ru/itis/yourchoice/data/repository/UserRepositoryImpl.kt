package ru.itis.yourchoice.data.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.User
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val USER_ID = "user_id"
private const val USER_NAME = "name"
private const val USER_EMAIL = "email"
private const val USER_PHONE = "phone"
private const val USER_LOCATION = "location"
private const val USER_PHOTO = "photo"
private const val USERS = "users"
private const val INTERESTS = "interests"

class UserRepositoryImpl
@Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val db: FirebaseFirestore,
        private val phoneAuthProvider: PhoneAuthProvider
) : UserRepository {

    override fun loginWithGoogle(acct: GoogleSignInAccount): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithCredential(
                    GoogleAuthProvider.getCredential(
                            acct.idToken,
                            null
                    )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(task.exception ?: Exception(""))
                }
            }
        }
                .doOnComplete {
                    searchUserInDb(acct.email, null)
                            .observeOn(Schedulers.io())
                            .subscribe({
                                if (!it) {
                                    Log.d("URL", "${acct.photoUrl}")
                                    addUserToDb(acct.displayName, acct.email, null, acct.photoUrl.toString())
                                }
                            }, {
                                Log.d("MYLOG", "err")
                            })
                }
    }

    override fun loginWithPhone(
            storedVerificationId: String,
            verificationCode: String,
            userName: String,
            phone: String
    ): Completable {
        return Single.fromCallable { PhoneAuthProvider.getCredential(storedVerificationId, verificationCode) }
                .flatMapCompletable { credential ->
                    Completable.create { emitter ->
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                emitter.onComplete()
                            } else {
                                emitter.onError(task.exception ?: Exception(""))
                            }
                        }
                    }
                            .doOnComplete {
                                searchUserInDb(null, phone)
                                        .observeOn(Schedulers.io())
                                        .subscribe({
                                            if (!it) {
                                                addUserToDb(userName, null, phone, null)
                                            }
                                        }, {})
                            }
                }
    }

    override fun sendVerificationCode(phoneNumber: String): Maybe<String> {
        return Maybe.create { emitter ->
            phoneAuthProvider.verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            // This callback will be invoked in two situations:
                            // 1 - Instant verification. In some cases the phone number can be instantly
                            //     verified without needing to send or enter a verification code.
                            // 2 - Auto-retrieval. On some devices Google Play services can automatically
                            //     detect the incoming verification SMS and perform verification without
                            //     user action.
                            emitter.onComplete()
                            Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            emitter.onError(e)
                        }

                        override fun onCodeSent(
                                verificationId: String?,
                                token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            // The SMS verification code has been sent to the provided phone number, we
                            // now need to ask the user to enter the code and then construct a credential
                            // by combining the code with a verification ID.
                            Log.d("MYLOG", "onCodeSent:" + verificationId!!)

                            // Save verification ID and resending token so we can use them later
                            emitter.onSuccess(verificationId)
                            // ...
                        }
                    })
        }
    }

    override fun getCurrentUser(): Single<User> {
        return Single.create { emitter ->
            db.collection(USERS)
                    .whereEqualTo(USER_ID, firebaseAuth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            emitter.onSuccess(mapDocumentToUser(document))
                        }
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    override fun checkAuthUser(): Single<Boolean> = Single.just(firebaseAuth.currentUser != null)

    override fun addUserToDb(name: String?, email: String?, phone: String?, photo: String?) {
        val userMap = HashMap<String, Any?>()
        userMap[USER_ID] = firebaseAuth.currentUser?.uid
        userMap[USER_NAME] = name
        userMap[USER_EMAIL] = email
        userMap[USER_PHONE] = phone
        userMap[USER_LOCATION] = null
        userMap[USER_PHOTO] = photo
        db.collection(USERS)
                .add(userMap)
                .addOnSuccessListener {
                    Log.d("MYLOG", it.toString())
                }.addOnFailureListener {
                    Log.d("MYLOG", it.message)
                }
    }

    override fun searchUserInDb(email: String?, phone: String?): Single<Boolean> {
        return Single.create { emitter ->
            db.collection(USERS)
                    .get()
                    .addOnSuccessListener { documents ->
                        val list: ArrayList<User> = ArrayList()
                        for ((i, document) in documents.withIndex()) {
                            list.add(mapDocumentToUser(document))
                            if (list[i].email == null) {
                                if (list[i].phone == phone) {
                                    emitter.onSuccess(true)
                                }
                            }
                            if (list[i].phone == null) {
                                if (list[i].email == email) {
                                    emitter.onSuccess(true)
                                }
                            }
                        }
                        emitter.onSuccess(false)
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    override fun getUserFromDbById(id: String): Single<User> {
        return Single.create { emitter ->
            db.collection(USERS)
                    .whereEqualTo(USER_ID, id)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            if (document != null) {
                                emitter.onSuccess(mapDocumentToUser(document))
                            } else {
                                Log.d("MYLOG", "No such document")
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    override fun setUsersCity(id: Int): Single<Int> =
            getDocumentId()
                    .flatMap {
                        setUsersCityIntoDb(id, it)
                    }

    private fun getDocumentId(): Single<String> {
        return Single.create { emitter ->
            db.collection(USERS)
                    .whereEqualTo(USER_ID, firebaseAuth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            emitter.onSuccess(document.id)
                        }
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    private fun setUsersCityIntoDb(cityId: Int, docId: String): Single<Int> {
        return Single.create { emitter ->
            db.collection(USERS).document(docId)
                    .update(USER_LOCATION, cityId)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onSuccess(cityId)
                        } else {
                            emitter.onError(task.exception ?: Exception(""))
                        }
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    override fun updatePostsListWithUserName(posts: List<Post>): Single<List<Post>> {
        return Single.create { emitter ->
            db.collection(USERS)
                    .get()
                    .addOnSuccessListener { documents ->
                        posts.forEach { post ->
                            documents.forEach { document ->
                                if (document.get(USER_ID).toString() == post.ownerId) {
                                    post.owner = mapDocumentToUser(document)
                                }
                            }
                            // фильтр не работает !!!!
/*                            documents.filter { it.get(USER_ID).toString() == post.ownerId }
                            documents.filter { document -> document.get(USER_ID).toString() == post.ownerId }
                            documents.map {
                                post.owner = mapDocumentToUser(it)
                            }*/
                        }
                        emitter.onSuccess(posts)
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }

        }
    }

    private fun mapDocumentToUser(documentSnapshot: QueryDocumentSnapshot): User =
            User(
                    documentSnapshot.get(USER_ID).toString(),
                    documentSnapshot.get(USER_NAME).toString(),
                    documentSnapshot.get(USER_EMAIL).toString(),
                    documentSnapshot.get(USER_PHONE).toString(),
                    documentSnapshot.get(USER_LOCATION).toString(),
                    documentSnapshot.get(USER_PHOTO).toString()
            )
}
