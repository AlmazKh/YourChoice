package ru.itis.yourchoice.data.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.itis.yourchoice.core.interfaces.UserRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val USER_NAME = "name"
private const val USER_EMAIL = "email"
private const val USER_PHONE = "phone"
private const val USER_LOCATION = "location"
private const val USER_PHOTO = "photo"
private const val USERS = "users"

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val phoneAuthProvider: PhoneAuthProvider
) : UserRepository {

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

    override fun sendVerificationCode(phoneNumber: String): Maybe<String> {
        return Maybe.create { emitter ->
            phoneAuthProvider.verifyPhoneNumber(
                phoneNumber,      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                TaskExecutors.MAIN_THREAD,  // Activity (for callback binding)
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        emitter.onComplete()
                        loginPhone(credential)
                        Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w(ContentValues.TAG, "onVerificationFailed", e)

                        if (e is FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            // ...
                        } else if (e is FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            // ...
                        }

                        emitter.onError(java.lang.Exception("ALL BAD"))

                        // Show a message and update the UI
                        // ...
                    }

                    override fun onCodeSent(
                        verificationId: String?,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d(ContentValues.TAG, "onCodeSent:" + verificationId!!)

                        // Save verification ID and resending token so we can use them later
                        emitter.onSuccess(verificationId)
                        // ...
                    }
                })
        }
    }

    override fun getCurrentUser() : FirebaseUser?{
        return firebaseAuth.currentUser
    }

    override fun addUserToDb(name: String?, email: String?, phone: String?) {
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
