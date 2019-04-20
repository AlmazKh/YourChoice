package ru.itis.yourchoice.view.addpost

import com.arellomobile.mvp.MvpView
import com.google.firebase.firestore.QuerySnapshot
import ru.itis.yourchoice.core.model.Category

interface AddPostView : MvpView {
    fun getCategories(mainCategory: Int)
    fun addPost(mainCategory: Int, category: String, description: String?)
    fun updateUI(categories: List<String>)
}