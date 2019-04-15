package ru.itis.yourchoice.core.interactors

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.PhoneAuthCredential
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun loginGoogle(acct: GoogleSignInAccount): Completable {
        return userRepository.loginGoogle(acct)
            .subscribeOn(Schedulers.io())
    }

    fun  loginPhone(credential: PhoneAuthCredential): Completable {
        return userRepository.loginPhone(credential)
            .subscribeOn(Schedulers.io())
    }

    fun addUserToDb(name: String?, email: String?, phone: String?) {
        userRepository.addUserToDb(name, email, phone)
    }
}