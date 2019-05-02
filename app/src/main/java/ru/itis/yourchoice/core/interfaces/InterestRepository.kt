package ru.itis.yourchoice.core.interfaces

import io.reactivex.Single
import ru.itis.yourchoice.core.model.Interest

interface InterestRepository {
    fun getUsersInterests(): Single<List<Interest>>
}
