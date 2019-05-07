package ru.itis.yourchoice.core.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.PropertyName

data class Post(
    @get:PropertyName("subcategory_id")
    @set:PropertyName("subcategory_id")
    var subcategory: Subcategory?,
    @get:PropertyName("name")
    @set:PropertyName("name")
    var postName: String?,
    var description: String?,
    @get:PropertyName("owner_id")
    @set:PropertyName("owner_id")
    var owner: User?
) {

    constructor() : this(Subcategory(0, "", 0), "", "", User())

    data class Builder(
        var subcategory: Subcategory? = null,
        var postName: String? = null,
        var description: String? = null,
        var owner: User? = null
    ) {
        fun subcategory(subcategory: Subcategory) = apply { this.subcategory = subcategory }
        fun postName(postName: String?) = apply { this.postName = postName }
        fun description(description: String?) = apply { this.description = description }
        fun owner(owner: User?) = apply { this.owner = owner }
        fun build() = Post(subcategory, postName, description, owner)
    }

}


//class FoodOrder(
//    val bread: String?,
//    val condiments: String?,
//    val meat: String?,
//    val fish: String?) {
//
//    data class Builder(
//        var bread: String? = null,
//        var condiments: String? = null,
//        var meat: String? = null,
//        var fish: String? = null) {
//
//        fun bread(bread: String) = apply { this.bread = bread
//            fun condiments(condiments: String) = apply { this.condiments = condiments }
//            fun meat(meat: String) = apply { this.meat = meat }
//            fun fish(fish: String) = apply { this.fish = fish }
//            fun build() = FoodOrder(bread, condiments, meat, fish)
//        }
//    }