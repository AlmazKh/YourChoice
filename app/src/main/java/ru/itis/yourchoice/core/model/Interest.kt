package ru.itis.yourchoice.core.model

import com.google.firebase.firestore.PropertyName

data class Interest(
    @get:PropertyName("subcategory_id")
    @set:PropertyName("subcategory_id")
    var subcategoryId: Int,
    @get:PropertyName("owner_id")
    @set:PropertyName("owner_id")
    var ownerId: String
) {
    constructor() : this(0, "")
}
