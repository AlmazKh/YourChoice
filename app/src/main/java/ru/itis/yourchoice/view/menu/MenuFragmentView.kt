package ru.itis.yourchoice.view.menu

import ru.itis.yourchoice.core.model.User

interface MenuFragmentView {
    fun openProfilePage()
    fun openLikesPage()
    fun openInterestsPage()
    fun openSettingsPage()
    fun openHelpAndFeedbackPage()
    fun updateUserInfo(user: User)
    fun openGPayPage()
}
