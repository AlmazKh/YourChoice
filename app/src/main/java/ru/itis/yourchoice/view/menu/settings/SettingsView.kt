package ru.itis.yourchoice.view.menu.settings

import ru.itis.yourchoice.core.model.City

interface SettingsView {

    fun showChangeLocationDialog(cities: ArrayList<String>)
    fun showSuccess(message: String)
}