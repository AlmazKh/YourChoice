package ru.itis.yourchoice.di.module

import dagger.Binds
import dagger.Module
import ru.itis.yourchoice.core.interfaces.*
import ru.itis.yourchoice.data.repository.*
import javax.inject.Singleton

@Module
interface RepoModule {
    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindInterestRepository(interestRepositoryImpl: InterestRepositoryImpl): InterestRepository

    @Binds
    @Singleton
    fun  bindCityRepository(cityRepositoryImpl: CityRepositoryImpl): CityRepository

    @Binds
    @Singleton
    fun bindHelpRepository(helpRepositoryImpl: HelpRepositoryImpl): HelpRepository
}
