package ru.itis.yourchoice.presenter.menu

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.InterestsInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.menu.interests.InterestsView
import javax.inject.Inject

class InterestsPresenter
@Inject constructor(
    private val interestsInteractor: InterestsInteractor
) : BasePresenter<InterestsView>() {

    fun getUsersInterests() {
        interestsInteractor.getUsersInterests()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.updateInterestsList(it)
                }, {
                    it.printStackTrace()
                })
    }
}