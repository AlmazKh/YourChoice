package ru.itis.yourchoice.view.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface LoginWithPhoneView : MvpView {
    fun sendVerificationCode()
    fun verifySignInCode()
    fun updateUI()
    fun signInSuccess()
    fun codeIsSending()
    fun showError(errorText: String)
}
