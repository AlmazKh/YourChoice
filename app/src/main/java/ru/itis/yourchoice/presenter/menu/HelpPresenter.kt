package ru.itis.yourchoice.presenter.menu

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.HelpInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.menu.help.HelpView
import javax.inject.Inject

class HelpPresenter
@Inject constructor(
        private val helpInteractor: HelpInteractor
) : BasePresenter<HelpView>() {

    fun sendMessage(message: String) {
        helpInteractor.sendMessage(message)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showSuccess()
                }, {
                    it.printStackTrace()
                })
    }
}