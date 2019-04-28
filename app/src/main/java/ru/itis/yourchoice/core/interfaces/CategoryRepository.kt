package ru.itis.yourchoice.core.interfaces

import io.reactivex.Maybe
import ru.itis.yourchoice.core.model.Category

interface CategoryRepository {
    fun getSubcategories (category: Int) : Maybe<List<Category>>
}
