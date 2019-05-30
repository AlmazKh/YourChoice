package ru.itis.yourchoice.data.db.converters

import android.arch.persistence.room.TypeConverter
import ru.itis.yourchoice.core.model.Subcategory

class SubcategoryConverter {

    @TypeConverter
    fun fromSubcategory(subcategory: Subcategory): Int? = subcategory.id

    @TypeConverter
    fun toSubcategory(id: Int?): Subcategory =
        Subcategory(0, "", 0)
}
