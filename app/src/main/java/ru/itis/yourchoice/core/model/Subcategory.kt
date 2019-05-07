package ru.itis.yourchoice.core.model

data class Subcategory(
        val id: Int?,
        var name: String?,
        var categoryId: Int?
) {
    data class Builder(
        var id: Int? = null,
        var name: String? = null,
        var categoryId: Int? = null
    ) {
        fun id(id: Int?) = apply { this.id = id }
        fun name(name: String?) = apply { this.name = name }
        fun categoryId(categoryId: Int?) = apply { this.categoryId = categoryId }
        fun build() = Subcategory(id, name, categoryId)
    }
}