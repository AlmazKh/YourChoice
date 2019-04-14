package ru.itis.yourchoice.view.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface LoginView : MvpView {
    fun signInGoogle()
    fun signInPhone()
    fun updateUI()
    fun signInSuccess()
    fun showError(errorText: String)
}