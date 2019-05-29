package ru.itis.yourchoice.di.module

import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.core.interactors.InterestsInteractor
import ru.itis.yourchoice.core.interactors.UserProfileInteractor
import ru.itis.yourchoice.core.interfaces.CategoryRepository
import ru.itis.yourchoice.core.interfaces.InterestRepository
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.presenter.menu.UserProfilePresenter

@Module
class MenuModule {

    @ScreenScope
    @Provides
    fun provideUserProfilePresenter(userProfileInteractor: UserProfileInteractor): UserProfilePresenter =
            UserProfilePresenter(userProfileInteractor)

    @ScreenScope
    @Provides
    fun provideInterestsInteractor(interestRepository: InterestRepository, categoryRepository: CategoryRepository): InterestsInteractor =
            InterestsInteractor(interestRepository, categoryRepository)
}