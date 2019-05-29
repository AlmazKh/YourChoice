package ru.itis.yourchoice.core.interactors

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.itis.yourchoice.core.interfaces.CityRepository
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.core.model.City
import ru.itis.yourchoice.core.model.User
import javax.inject.Inject

class SettingsInteractor
@Inject constructor(
        private val cityRepository: CityRepository,
        private val userRepository: UserRepository
) {

    fun getCities(): Single<List<City>> =
            cityRepository.getCities()
                    .subscribeOn(Schedulers.io())

    fun setUsersCity(id: Int): Single<City> =
            userRepository.setUsersCity(id)
                    .flatMap {
                        cityRepository.getCityById(id)
                    }
                    .subscribeOn(Schedulers.io())

    fun getCurrentUser(): Single<User> =
            userRepository.getCurrentUser()
                    .subscribeOn(Schedulers.io())
}