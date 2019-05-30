package ru.itis.yourchoice.core.interactors

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.InterestRepository
import ru.itis.yourchoice.core.model.Interest
import ru.itis.yourchoice.core.model.Subcategory
import javax.inject.Inject

class InterestsInteractor
@Inject constructor(
    private val interestRepository: InterestRepository,
    private val categoryRepository: CategoryRepository
){

    fun getUsersInterests(): Single<List<Subcategory>> =
        interestRepository.getUsersInterests()
                .map {
                    val list = mutableListOf<Subcategory>()
                    it.forEach {
                        categoryRepository.getSubcategoryById(it.subcategoryId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe ({
                                    list.add(it)
                                }, {})
                    }
                    list.toList()
                }
                .subscribeOn(Schedulers.io())

    fun checkInterest(subcategoryId: Int): Single<Boolean> =
        interestRepository.checkInterest(subcategoryId)
            .subscribeOn(Schedulers.io())

    fun changeInterestState(state: Boolean, subcategoryId: Int): Completable {
        return if (state) {
            interestRepository.addInterest(subcategoryId)
        } else {
            interestRepository.getDocumentId(subcategoryId)
                    .flatMapCompletable {
                        interestRepository.deleteInterest(it)
                    }
                    .subscribeOn(Schedulers.io())
        }
    }
}