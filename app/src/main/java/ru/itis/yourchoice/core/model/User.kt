package ru.itis.yourchoice.core.model

import android.provider.ContactsContract

data class User (
    val id: String?,
    var name: String?,
    val email: String?,
    val phone: String?,
    val location: String?,
    val photo: String?
) {
    constructor() : this("", "", "", "", "", "")

    data class Builder(
        var id: String? = null,
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var location: String? = null,
        var photo: String? = null
    ) {
        fun id(id: String?) = apply { this.id = id }
        fun name(name: String?) = apply { this.name = name }
        fun email(email: String?) = apply { this.email = email }
        fun phone(phone: String?) = apply { this.phone = phone }
        fun location(location: String?) = apply { this.location = location }
        fun photo(photo: String?) = apply { this.photo = photo }
        fun build() = User(id, name, email, phone, location, photo)
    }
}