package ru.itis.yourchoice.data.repository

import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.CityRepository
import ru.itis.yourchoice.core.model.City
import ru.itis.yourchoice.data.CitiesHolder
import javax.inject.Inject

class CityRepositoryImpl
@Inject constructor(
        private val citiesHolder: CitiesHolder
) : CityRepository {

    override fun getCities(): Single<List<City>> = Single.just(citiesHolder.getCities())

    override fun getCityById(id: Int): Single<City> {
        return Single.create { emitter ->
            citiesHolder.getCities().forEach { city -> if(city.id == id){emitter.onSuccess(city)}}
        }
    }
}