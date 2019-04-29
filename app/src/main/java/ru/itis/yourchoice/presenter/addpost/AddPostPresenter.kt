package ru.itis.yourchoice.presenter.addpost

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.AddPostInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.addpost.AddPostView

class AddPostPresenter(
        private val addPostInteractor: AddPostInteractor
) : BasePresenter<AddPostView>() {
    fun getMainCategories(): ArrayList<String> = addPostInteractor.getMainCategories()

    fun getSubcategories(mainCategory: Any?) {
        addPostInteractor.getSubcategories(mainCategory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val list: ArrayList<String> = ArrayList()
                    it.forEach { list.add(it.subcategoryName) }
                    view?.updateUI(list)
                }, {

                })
    }

    fun addPost(mainCategory: Any, category: String, postName: String, description: String) {
        addPostInteractor.addPostIntoDb(
                mainCategory,
                category,
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
