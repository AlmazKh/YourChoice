package ru.itis.yourchoice.core.interfaces

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import io.reactivex.Completable
import io.reactivex.Maybe

interface UserRepository {

    fun loginGoogle(acct: GoogleSignInAccount): Completable
    fun loginPhone(credential: PhoneAuthCredential): Completable
    fun sendVerificationCode(phoneNumber: String): Maybe<String>
    fun addUserToDb(name: String?, email: String?, phone: String?)
    fun getCurrentUser() : FirebaseUser?
}
