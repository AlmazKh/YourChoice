package ru.itis.yourchoice.core.interfaces

import io.reactivex.Single
import ru.itis.yourchoice.core.model.City

interface CityRepository {

    fun getCities(): Single<List<City>>
    fun getCityById(id: Int): Single<City>
}