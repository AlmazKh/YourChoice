package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable

interface HelpRepository {

    fun sendFeedback(message: String): Completable
}
