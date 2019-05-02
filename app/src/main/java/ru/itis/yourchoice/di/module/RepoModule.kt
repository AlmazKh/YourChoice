package ru.itis.yourchoice.di.module

import dagger.Binds
import dagger.Module
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.InterestRepository
import ru.itis.yourchoice.core.interfaces.PostRepository
import ru.itis.yourchoice.core.interfaces.UserRepository
import ru.itis.yourchoice.data.repository.CategoryRepositoryImpl
import ru.itis.yourchoice.data.repository.InterestRepositoryImpl
import ru.itis.yourchoice.data.repository.PostRepositoryImpl
import ru.itis.yourchoice.data.repository.UserRepositoryImpl
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
}
