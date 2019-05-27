package ru.itis.yourchoice.data.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.Subcategory
import ru.itis.yourchoice.data.CategoriesHolder
import ru.itis.yourchoice.data.SubcategoriesHolder
import javax.inject.Inject

class CategoryRepositoryImpl
@Inject constructor(
    private val categoriesHolder: CategoriesHolder,
    private val subcategoriesHolder: SubcategoriesHolder
): CategoryRepository {

    override fun getCategories(): Single<List<Category>> = Single.just(categoriesHolder.getCategories())

    override fun getSubcategories(category: Int): Single<List<Subcategory>> {
        return Single.create { emitter ->
            val list: ArrayList<Subcategory> = ArrayList()
            for (subcategory in subcategoriesHolder.getSubcategories()) {
                if (subcategory.categoryId == category) {
                    list.add(subcategory)
                }
            }
            emitter.onSuccess(list)
        }
    }

    override fun getCategoryByName(name: String): Single<Category> {
        return Single.create{ emitter ->
            for(category in categoriesHolder.getCategories()) {
                if(category.name == name) {
                    emitter.onSuccess(category)
                }
            }
        }
    }

    override fun getSubategoryByNameAndCategoryId(name: String, categoryId: Int): Single<Subcategory> {
        return Single.create{ emitter ->
            for(subcategory in subcategoriesHolder.getSubcategories()) {
                if(subcategory.name == name && subcategory.categoryId == categoryId) {
                    emitter.onSuccess(subcategory)
                }
            }
        }
    }

    override fun updatePostsListWithCategory(posts: List<Post>): Single<List<Post>> {
        return Single.fromCallable {
            posts.forEach { post -> post.subcategory = subcategoriesHolder.getSubcategories().firstOrNull { it.id == post.subcategoryId } }
            posts
        }
    }
}
