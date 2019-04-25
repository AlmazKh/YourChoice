package ru.itis.yourchoice.view.login

interface LoginView {
    fun showLoginWithPhoneDialog()
    fun signInSuccess()
    fun showError(errorText: String)
}