package ru.itis.yourchoice.presenter.login

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.login.LoginWithPhoneView

const val PHONE_NUMBER_LENGHT = 10

class LoginWithPhoneDialogPresenter(
    private val loginInteractor: LoginInteractor
) : BasePresenter<LoginWithPhoneView>() {

    private var storedVerificationId: String? = null
    private var storedPhoneNumber: String? = null

    fun sendVerificationCode(phoneNumber: String) {
        if (phoneNumber.isEmpty()) {
            view?.showErrorPhoneNumberFormat()
            return
        }
        if (phoneNumber.length < PHONE_NUMBER_LENGHT) {
            view?.showErrorPhoneNumberFormat()
            return
        }
        loginInteractor.sendVerificationCode(phoneNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.codeIsSending()
                storedVerificationId = it
                storedPhoneNumber = phoneNumber
            }, {
                view?.showError(it.toString())
            }, {
                Log.d("MYLOG", "success send verif")
                view?.signInSuccess()
            })
        Log.d("MYLOG", "presenter sendCode")
    }

    fun verifySignInCode(verificationCode: String, userName: String, phoneNumber: String) {
        if (storedVerificationId == null) {
            view?.showErrorVerificationCode()
            return
        }
        if (storedPhoneNumber == null) {
            view?.showErrorPhoneNumberFormat()
            return
        }
        if(storedPhoneNumber != phoneNumber) {
            view?.showStoredAndWritedPhonesNumberMismatch()
        }
        disposables.add(
            loginInteractor.loginWithPhone(storedVerificationId!!, verificationCode, userName, phoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("MYLOG", "success verif")
                    view?.signInSuccess()
                }, {
                    view?.showError(it.message ?: "Login error")
                    it.printStackTrace()
                })
        )
    }
}
