package ru.itis.yourchoice.view.addpost

interface AddPostView {
    fun updateUI(categories: List<String>)
    fun postAddedSuccessful()
    fun showError(errorText: String)
    fun checkFields(): Boolean
    fun cleanFields()
    fun showFieldsFillError()
}
