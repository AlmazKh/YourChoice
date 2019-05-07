package ru.itis.yourchoice.core.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.itis.yourchoice.data.db.converters.SubcategoryConverter
import ru.itis.yourchoice.data.db.converters.UserConverter

@Entity(tableName = "post")
data class Post(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @SerializedName("subcategory_id")
    @Expose
    @get:PropertyName("subcategory_id")
    @set:PropertyName("subcategory_id")
    @TypeConverters(SubcategoryConverter::class)
    var subcategory: Subcategory?,

    @SerializedName("name")
    @Expose
    @get:PropertyName("name")
    @set:PropertyName("name")
    var postName: String?,

    @SerializedName("description")
    @Expose
    var description: String?,

    @TypeConverters(UserConverter::class)
    @SerializedName("owner_id")
    @Expose
    @get:PropertyName("owner_id")
    @set:PropertyName("owner_id")
    var owner: User?
) {
    data class Builder(
        var id: Long? = null,
        var subcategory: Subcategory? = null,
        var postName: String? = null,
        var description: String? = null,
        var owner: User? = null
    ) {
        fun id(id: Long?) = apply { this.id = id }
        fun subcategory(subcategory: Subcategory) = apply { this.subcategory = subcategory }
        fun postName(postName: String?) = apply { this.postName = postName }
        fun description(description: String?) = apply { this.description = description }
        fun owner(owner: User?) = apply { this.owner = owner }
        fun build() = Post(id, subcategory, postName, description, owner)
    }
}
