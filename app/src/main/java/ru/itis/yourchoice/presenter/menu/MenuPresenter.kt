package ru.itis.yourchoice.presenter.menu

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.menu.MenuFragmentView
import javax.inject.Inject

class MenuPresenter
@Inject constructor(
        private val userRepository: UserRepository
) : BasePresenter<MenuFragmentView>() {

    fun getCurrentUser() {
        disposables.add(
                userRepository.getCurrentUser()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view?.updateUserInfo(it)
                        }, {
                            it.printStackTrace()
                        })
        )
    }
}
