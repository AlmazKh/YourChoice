package ru.itis.yourchoice.core.model

import com.google.firebase.auth.FirebaseUser

data class Post(
    val ownerId: String,
    val mainCategoryId: Int,
    val categoryName: String,
    val description: String
) {
    constructor() : this("", 0, "", "")
}