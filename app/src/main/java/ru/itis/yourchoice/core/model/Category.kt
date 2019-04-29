package ru.itis.yourchoice.core.model

import com.google.firebase.firestore.PropertyName

data class Category(
    val id: Int,
    @get:PropertyName("name")
    @set:PropertyName("name")
    var subcategoryName: String,
    @get:PropertyName("category_id")
    @set:PropertyName("category_id")
    var categoryId: Int?
) {
    constructor() : this(0, "", null)
}
