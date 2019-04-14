package ru.itis.yourchoice.di.module

import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.data.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    fun provideLoginInteractor(
        userRepository: UserRepository
    ): LoginInteractor = LoginInteractor(userRepository)
}