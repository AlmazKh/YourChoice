package ru.itis.yourchoice.data.repository

import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

private const val USER_NAME = "name"
private const val USER_EMAIL = "email"
private const val USER_PHONE = "phone"
private const val USER_LOCATION = "location"
private const val USER_PHOTO = "photo"
private const val USERS = "users"

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): UserRepository {

    override fun loginGoogle(acct: GoogleSignInAccount): Completable {
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
    }

    override fun loginPhone(credential: PhoneAuthCredential): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(task.exception ?: Exception(""))
                }
            }
        }
    }

    override fun addUserToDb(name: String?, email: String?, phone: Int?) {
        val userMap = HashMap<String, Any?>()
        userMap[USER_NAME] = name
        userMap[USER_EMAIL] = email
        userMap[USER_PHONE] = phone
        userMap[USER_LOCATION] = null
        userMap[USER_PHOTO] = null
        db.collection(USERS)
            .add(userMap)
            .addOnSuccessListener {
                Log.d("MYLOG", it.toString())
            }.addOnFailureListener {
                Log.d("MYLOG", it.message)
            }
    }
}
