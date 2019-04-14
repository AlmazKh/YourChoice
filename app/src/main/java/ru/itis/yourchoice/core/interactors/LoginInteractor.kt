package ru.itis.yourchoice.core.interactors

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun login(acct: GoogleSignInAccount): Completable {
        return userRepository.login(acct)
            .subscribeOn(Schedulers.io())
    }
}