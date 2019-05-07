package ru.itis.yourchoice.data.db.converters

import android.arch.persistence.room.TypeConverter
import ru.itis.yourchoice.core.model.User

class UserConverter {

    @TypeConverter
    fun fromUser(user: User): String? = user.id

    @TypeConverter
    fun toUser(id: String): User =
            User.Builder()
                .id(id)
                .build()
}