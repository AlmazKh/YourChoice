package ru.itis.yourchoice.core.model

data class User (
    val id: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val location: String?,
    val photo: String?
)
