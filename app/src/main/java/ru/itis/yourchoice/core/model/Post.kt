package ru.itis.yourchoice.core.model

import com.google.firebase.auth.FirebaseUser

data class Post(
    val categoryId: String,
    val subcategoryId: String,
    val postName: String,
    val description: String,
    val ownerId: String
) {
    constructor() : this("", "", "", "", "")
}
