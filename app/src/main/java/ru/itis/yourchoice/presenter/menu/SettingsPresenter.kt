package ru.itis.yourchoice.presenter.menu

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.SettingsInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.menu.settings.SettingsView
import javax.inject.Inject

class SettingsPresenter
@Inject constructor(
        private val settingsInteractor: SettingsInteractor
) : BasePresenter<SettingsView>() {

    fun getCities() {
        disposables.add(
                settingsInteractor.getCities()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val list: ArrayList<String> = arrayListOf()
                            it.forEach { city -> list.add(city.name) }
                            view?.showChangeLocationDialog(list)
                        }, {
                            it.printStackTrace()
                        })
        )
    }

    fun setUsersCity(id: Int) {
        disposables.add(
                settingsInteractor.setUsersCity(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view?.showSuccess("Location has changed", it)
                        }, {
                            it.printStackTrace()
                        })
        )
    }

    fun getCurrentUser() {
        disposables.add(
                settingsInteractor.getCurrentUser()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view?.setData(it)
                        }, {
                            it.printStackTrace()
                        })
        )
    }

}