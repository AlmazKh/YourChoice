package ru.itis.yourchoice.core.interactors

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.HelpRepository
import javax.inject.Inject

class HelpInteractor
@Inject constructor(
        private val helpRepository: HelpRepository
){

    fun sendMessage(message: String): Completable =
            helpRepository.sendFeedback(message)
                    .subscribeOn(Schedulers.io())
}
