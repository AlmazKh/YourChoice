package ru.itis.yourchoice.core.model


import ru.itis.yourchoice.data.db.converters.SubcategoryConverter
import ru.itis.yourchoice.data.db.converters.UserConverter

data class Post(
    val subcategoryId: Int,
    val postName: String?,
    val description: String?,
    val ownerId: String,
    var subcategory: Subcategory?,
    var owner: User?
)
