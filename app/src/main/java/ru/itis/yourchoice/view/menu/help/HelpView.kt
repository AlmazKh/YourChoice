package ru.itis.yourchoice.view.menu.help

interface HelpView {

    fun checkFields(): Boolean
    fun cleanFields()
    fun showSuccess()
    fun showError(errorText: String)
    fun showFieldsFillError()
}
