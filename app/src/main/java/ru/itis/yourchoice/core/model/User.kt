package ru.itis.yourchoice.core.model

import android.provider.ContactsContract

data class User (
    val name: String,
    val email: String?,
    val phone: String?,
    val location: String?,
    val photo: String?
) {
}