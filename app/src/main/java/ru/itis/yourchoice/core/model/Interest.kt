package ru.itis.yourchoice.core.model

import com.google.firebase.firestore.PropertyName

data class Interest(
    var subcategoryId: Int,
    var ownerId: String
)
