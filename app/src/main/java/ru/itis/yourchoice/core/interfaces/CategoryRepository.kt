package ru.itis.yourchoice.core.interfaces

import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Subcategory

interface CategoryRepository {
    fun getSubcategories(category: Int): Single<List<Subcategory>>
    fun getCategories(): Single<List<Category>>
    fun getCategoryIdByName(name: String): Single<Category>
    fun getSubategoryIdByName(name: String): Single<Subcategory>
}
