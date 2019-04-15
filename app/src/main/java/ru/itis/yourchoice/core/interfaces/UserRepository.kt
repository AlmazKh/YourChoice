package ru.itis.yourchoice.core.interfaces

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.PhoneAuthCredential
import io.reactivex.Completable

interface UserRepository {

    fun loginGoogle(acct: GoogleSignInAccount): Completable
    fun loginPhone(credential: PhoneAuthCredential): Completable
    fun addUserToDb(name: String?, email: String?, phone: String?)

}