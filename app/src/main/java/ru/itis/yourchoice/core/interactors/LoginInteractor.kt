package ru.itis.yourchoice.core.interactors

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor
@Inject constructor(
    private val userRepository: UserRepository
) {

    fun loginWithGoogle(acct: GoogleSignInAccount): Completable =
        userRepository.loginWithGoogle(acct)
            .subscribeOn(Schedulers.io())


    fun loginWithPhone(storedVerificationId: String, verificationCode: String, userName: String, phone: String): Completable {
        return userRepository.loginWithPhone(storedVerificationId, verificationCode, userName, phone)
            .subscribeOn(Schedulers.io())
    }

    fun sendVerificationCode(phoneNumber: String): Maybe<String> =
        userRepository.sendVerificationCode(phoneNumber)
            .subscribeOn(Schedulers.io())

    fun checkAuthUser(): Single<Boolean> =
            userRepository.checkAuthUser()
                    .subscribeOn(Schedulers.io())
}
