package ru.itis.yourchoice.core.interactors

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor
@Inject constructor(
    private val userRepository: UserRepository
) {

    fun loginGoogle(acct: GoogleSignInAccount): Completable {
        return userRepository.loginGoogle(acct)
            .subscribeOn(Schedulers.io())
    }

    fun loginPhone(storedVerificationId: String, verificationCode: String): Completable {
        return userRepository.loginPhone(
            PhoneAuthProvider.getCredential(
                storedVerificationId,
                verificationCode
            )
        ).subscribeOn(Schedulers.io())
    }

    fun sendVerificationCode(phoneNumber: String): Maybe<String> =
        userRepository.sendVerificationCode(phoneNumber)
            .subscribeOn(Schedulers.io())

    fun addUserToDb(name: String?, email: String?, phone: String?) {
        userRepository.addUserToDb(name, email, phone)
    }

    fun getCurrentUser(): FirebaseUser? {
        return userRepository.getCurrentUser()
    }
}
