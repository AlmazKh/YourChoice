package ru.itis.yourchoice.view.menu.settings

import ru.itis.yourchoice.core.model.User

interface SettingsView {

    fun showChangeLocationDialog(cities: ArrayList<String>)
    fun showSuccess(message: String)
    fun setData(user: User)
}