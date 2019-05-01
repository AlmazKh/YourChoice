package ru.itis.yourchoice.view.addpost

interface AddPostView {
    fun updateUIwithMainCategories(categories: List<String>)
    fun updateUIwithSubcategories(categories: List<String>)
    fun postAddedSuccessful()
    fun showError(errorText: String)
    fun checkFields(): Boolean
    fun cleanFields()
    fun showFieldsFillError()
}
