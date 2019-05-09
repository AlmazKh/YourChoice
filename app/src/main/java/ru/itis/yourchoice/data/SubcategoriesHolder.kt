package ru.itis.yourchoice.data

import ru.itis.yourchoice.core.model.Subcategory
import java.lang.Exception
import javax.inject.Inject

class SubcategoriesHolder @Inject constructor() {

    private val subcategories: MutableList<Subcategory> = mutableListOf()

    init {
        //Films
        subcategories.add(Subcategory(1, "Action movies", 1))
        subcategories.add(Subcategory(2, "Detectives", 1))
        subcategories.add(Subcategory(3, "Documentary", 1))
        subcategories.add(Subcategory(4, "Dramas", 1))
        subcategories.add(Subcategory(5, "Historical", 1))
        subcategories.add(Subcategory(6, "Comedy", 1))
        subcategories.add(Subcategory(7, "Adventure", 1))
        subcategories.add(Subcategory(8, "Thrillers", 1))
        subcategories.add(Subcategory(9, "Fantasy", 1))
        subcategories.add(Subcategory(10, "Horrors", 1))
        //Series
        subcategories.add(Subcategory(20, "Detectives", 2))
        subcategories.add(Subcategory(21, "Documentary", 2))
        subcategories.add(Subcategory(22, "Dramas", 2))
        subcategories.add(Subcategory(23, "Historical", 2))
        subcategories.add(Subcategory(24, "Fantasy", 2))
        subcategories.add(Subcategory(25, "Horrors", 2))
        //Events
        subcategories.add(Subcategory(30, "Festivals", 3))
        subcategories.add(Subcategory(31, "Concerts", 3))
        subcategories.add(Subcategory(32, "Exhibitions", 3))
        subcategories.add(Subcategory(33, "Theater", 3))
        subcategories.add(Subcategory(34, "Sport", 3))
        subcategories.add(Subcategory(35, "Master-classes", 3))
        //Books
        subcategories.add(Subcategory(40, "Modern prose", 4))
        subcategories.add(Subcategory(41, "Comics", 4))
        subcategories.add(Subcategory(42, "Novels", 4))
        subcategories.add(Subcategory(43, "Dramas", 4))
        subcategories.add(Subcategory(44, "Culture and art", 4))
        subcategories.add(Subcategory(45, "Fantasy", 4))
        subcategories.add(Subcategory(46, "Horrors", 4))
        subcategories.add(Subcategory(47, "Dictionaries, reference books", 4))
        subcategories.add(Subcategory(48, "Detectives", 4))
    }

    fun getSubcategories(): List<Subcategory> = subcategories

    fun getSubcategoryById(id: Int): Subcategory? {
        getSubcategories().forEach { subcategory ->
            if (subcategory.id == id) {
                return subcategory
            }
        }
        return null
    }
}
