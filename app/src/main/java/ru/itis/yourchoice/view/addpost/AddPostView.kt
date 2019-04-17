package ru.itis.yourchoice.view.addpost

import com.arellomobile.mvp.MvpView

interface AddPostView : MvpView {
    fun getCategories(mainCategory: Int)
    fun addPost(mainCategory: Int, category: String, description: String?)
}