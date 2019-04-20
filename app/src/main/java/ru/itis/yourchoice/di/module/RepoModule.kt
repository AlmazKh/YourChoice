package ru.itis.yourchoice.di.module

import dagger.Binds
import dagger.Module
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.core.interfaces.AddPostRepository
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.data.repository.AddPostRepositoryImpl
import ru.itis.yourchoice.data.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
interface RepoModule {

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindAddPostrepository(addPostRepositoryImpl: AddPostRepositoryImpl): AddPostRepository

}