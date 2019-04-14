package ru.itis.yourchoice.core.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable

interface UserRepository {

    fun login(acct: GoogleSignInAccount): Completable
}