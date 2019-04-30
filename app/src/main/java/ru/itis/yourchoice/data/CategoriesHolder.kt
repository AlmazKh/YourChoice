package ru.itis.yourchoice.data

import ru.itis.yourchoice.core.model.Category
import javax.inject.Inject

class CategoriesHolder @Inject constructor() {

    private val categories: MutableList<Category> = mutableListOf()

    init {
        categories.add(Category(1, "Films"))
        categories.add(Category(2, "Series"))
        categories.add(Category(3, "Events"))
        categories.add(Category(4, "Books"))
    }

    fun getCategories(): List<Category> {
        return categories
    }
}