package ru.itis.yourchoice.presenter.addpost

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.AddPostInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.addpost.AddPostView

class AddPostPresenter(
        private val addPostInteractor: AddPostInteractor
) : BasePresenter<AddPostView>() {

    fun getMainCategories() {
        addPostInteractor.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val list: ArrayList<String> = ArrayList()
                    it.forEach { list.add(it.name) }
                    view?.updateUIwithMainCategories(list)
                }, {
                    it.printStackTrace()
                })
    }

    fun getSubcategories(category: String) {
        addPostInteractor.getSubcategories(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val list: ArrayList<String> = ArrayList()
                    it.forEach { list.add(it.name) }
                    view?.updateUIwithSubcategories(list)
                }, {
                    it.printStackTrace()
                })
    }

    fun addPost(subcategory: String, postName: String, description: String) {
        addPostInteractor.addPostIntoDb(
                subcategory,
                postName,
                description
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.postAddedSuccessful()
                }, {
                    view?.showError(it.message ?: "Post adding error. Please, try again")
                })
    }
}
