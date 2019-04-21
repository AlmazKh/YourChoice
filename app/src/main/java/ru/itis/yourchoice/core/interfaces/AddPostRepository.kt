package ru.itis.yourchoice.core.interfaces

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Maybe
import ru.itis.yourchoice.core.model.Category
import java.io.FileDescriptor

interface AddPostRepository {
    fun getCategories (category: Int) : Maybe<MutableList<Category>>
    fun addPostIntoDb (category: Int, subcategory: String, description: String) : Completable
}
