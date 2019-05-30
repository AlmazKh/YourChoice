package ru.itis.yourchoice.core.interfaces

import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.Subcategory

interface CategoryRepository {
    fun getSubcategoryById(id: Int): Single<Subcategory>
    fun getSubcategories(category: Int): Single<List<Subcategory>>
    fun getCategories(): Single<List<Category>>
    fun getCategoryByName(name: String): Single<Category>
    fun getSubategoryByNameAndCategoryId(name: String, categoryId: Int): Single<Subcategory>
    fun updatePostsListWithCategory(posts: List<Post>): Single<List<Post>>
}
