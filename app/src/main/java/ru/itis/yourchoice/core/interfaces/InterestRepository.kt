package ru.itis.yourchoice.core.interfaces

import io.reactivex.Completable
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Interest

interface InterestRepository {
    fun getUsersInterests(): Single<List<Interest>>
    fun checkInterest(subcategoryId: Int): Single<Boolean>
    fun deleteInterest(docId: String): Completable
    fun addInterest(subcategoryId: Int): Completable
    fun getDocumentId(subcategoryId: Int): Single<String>
}
