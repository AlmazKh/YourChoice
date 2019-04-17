package ru.itis.yourchoice.presenter.addpost

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.itis.yourchoice.core.interactors.AddPostInteractor
import ru.itis.yourchoice.view.addpost.AddPostView
import javax.inject.Inject

@InjectViewState
class AddPostPresenter
    @Inject constructor(): MvpPresenter<AddPostView>() {
//@Inject constructor(
//    private val addPostInteractor: AddPostInteractor
//)

    fun getCategories(mainCategory: Int) {
        TODO("not implemented") //ge
    }

    fun addPost(mainCategory: Int, category: String, description: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}