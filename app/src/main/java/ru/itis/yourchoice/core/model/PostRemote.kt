package ru.itis.yourchoice.core.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import ru.itis.yourchoice.data.db.converters.SubcategoryConverter
import ru.itis.yourchoice.data.db.converters.UserConverter

@Entity(tableName = "post_remote")
data class PostRemote(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val subcategoryId: Int,
    val postName: String?,
    val description: String?,
    val ownerId: String

//    @TypeConverters(SubcategoryConverter::class)
//    val subcategory: Subcategory?,
//
//    @TypeConverters(UserConverter::class)
//    val owner: User?
)