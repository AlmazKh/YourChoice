package ru.itis.yourchoice.data

import ru.itis.yourchoice.core.model.City
import javax.inject.Inject

class CitiesHolder @Inject constructor() {

    private val cities: MutableList<City> = mutableListOf()

    init {
        cities.add(City(0, "spb", "Санкт-Петербург"))
        cities.add(City(1, "msk", "Москва"))
        cities.add(City(2, "nsk", "Новосибирск"))
        cities.add(City(3, "ekb", "Екатеринбург"))
        cities.add(City(4, "nnv", "Нижний Новгород"))
        cities.add(City(5, "kzn", "Казань"))
        cities.add(City(6, "vbg", "Выборг"))
        cities.add(City(7, "smr", "Самара"))
        cities.add(City(8, "krd", "Краснодар"))
        cities.add(City(9, "sochi", "Сочи"))
        cities.add(City(10, "ufa", "Уфа"))
        cities.add(City(11, "krasnoyarsk", "Красноярск"))
        cities.add(City(12, "kev", "Киев"))
        cities.add(City(13, "new-york", "Нью-Йорк"))

    }

    fun getCities(): List<City> = cities
}
