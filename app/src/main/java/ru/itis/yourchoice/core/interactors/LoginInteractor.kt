package ru.itis.yourchoice.core.interactors

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun login(): Completable {
        return userRepository.login()
            .subscribeOn(Schedulers.io())
    }
}