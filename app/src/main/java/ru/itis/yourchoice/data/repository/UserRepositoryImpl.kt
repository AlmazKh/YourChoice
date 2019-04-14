package ru.itis.yourchoice.data.repository

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Completable
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): UserRepository {

    override fun login(acct: GoogleSignInAccount): Completable {
//        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
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
//        Completable.create {
//            firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d("MYLOG", "krasava")
//                        viewState.updateUI()
//                        // Sign in success, update UI with the signed-in user's information
////                    val user = mAuth.currentUser
////                    updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.d("MYLOG", "fail")
////                    updateUI(null)
//                    }
//
//                    // ...
//                }
//        }
    }
}