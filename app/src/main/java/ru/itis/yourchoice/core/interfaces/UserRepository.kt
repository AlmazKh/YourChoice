package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable

interface UserRepository {

    fun login(): Completable
}