package ru.itis.yourchoice.core.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import ru.itis.yourchoice.data.db.converters.SubcategoryConverter
import ru.itis.yourchoice.data.db.converters.UserConverter

@Parcelize
data class Post(
        val subcategoryId: Int,
        val postName: String?,
        val description: String?,
        val ownerId: String,
        var subcategory: @RawValue Subcategory?,
        var owner: @RawValue User?
) : Parcelable
