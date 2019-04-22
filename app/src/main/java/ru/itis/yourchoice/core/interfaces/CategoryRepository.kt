package ru.itis.yourchoice.core.interfaces

import io.reactivex.Maybe
import ru.itis.yourchoice.core.model.Category

interface CategoryRepository {
    fun getCategories (category: Int) : Maybe<MutableList<Category>>
}
