package ru.itis.yourchoice.di.module

import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.core.interactors.AddPostInteractor
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.presenter.addpost.AddPostPresenter

@Module
class AddPostModule {

    @ScreenScope
    @Provides
    fun provideAddPostPresenter(addPostInteractor: AddPostInteractor): AddPostPresenter =
            AddPostPresenter(addPostInteractor)
}
