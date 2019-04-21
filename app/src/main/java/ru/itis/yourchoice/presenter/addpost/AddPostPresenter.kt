package ru.itis.yourchoice.presenter.addpost

import android.content.ContentValues
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.AddPostInteractor
import ru.itis.yourchoice.view.addpost.AddPostView
import javax.inject.Inject

@InjectViewState
class AddPostPresenter
@Inject constructor(
    private val addPostInteractor: AddPostInteractor
) : MvpPresenter<AddPostView>() {
    fun getMainCategories() : ArrayList<String> = addPostInteractor.getMainCategories()

    fun getCategories(mainCategory: Any?) {
        addPostInteractor.getCategories(mainCategory)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val list: ArrayList<String> = ArrayList()
                it.forEach { list.add(it.name) }
                viewState.updateUI(list)
            }, {

            })
    }

    fun addPost(category: String, description: String) {
        addPostInteractor.addPostIntoDb(category, description)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.postAddedSuccessful()
            }, {
                viewState.showError(it.message ?: "Post adding error. Please, try again")
            })
    }
}
