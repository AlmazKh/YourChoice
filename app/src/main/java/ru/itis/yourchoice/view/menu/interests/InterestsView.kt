package ru.itis.yourchoice.view.menu.interests

import ru.itis.yourchoice.core.model.Subcategory

interface InterestsView {
    fun updateInterestsList(interests: List<Subcategory>)
}
