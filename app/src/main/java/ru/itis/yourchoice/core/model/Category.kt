package ru.itis.yourchoice.core.model

data class Category(
    val id: Int,
    val name: String,
    val category_id: Int?
) {
    constructor() : this(0, "", null)
}
