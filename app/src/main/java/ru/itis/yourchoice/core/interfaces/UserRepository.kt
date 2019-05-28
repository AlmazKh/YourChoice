package ru.itis.yourchoice.core.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface UserRepository {
    fun loginWithGoogle(acct: GoogleSignInAccount): Completable
    fun loginWithPhone(storedVerificationId: String, verificationCode: String, userName: String, phone: String): Completable
    fun sendVerificationCode(phoneNumber: String): Maybe<String>
    fun addUserToDb(name: String?, email: String?, phone: String?)
    fun searchUserInDb(email: String?, phone: String?): Single<Boolean>
    fun getCurrentUser(): Single<FirebaseUser?>
    fun checkAuthUser(): Single<Boolean>
}
