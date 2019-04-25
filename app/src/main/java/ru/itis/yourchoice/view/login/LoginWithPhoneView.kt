package ru.itis.yourchoice.view.login

import com.arellomobile.mvp.MvpView

interface LoginWithPhoneView : MvpView {
    fun sendVerificationCode()
    fun verifySignInCode()
    fun signInSuccess()
    fun codeIsSending()
    fun showError(errorText: String)
    fun showErrorPhoneNumberFormat()
    fun showErrorVerificationCode()
    fun showStoredAndWritedPhonesNumberMismatch()
}
