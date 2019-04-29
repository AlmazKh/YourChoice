package ru.itis.yourchoice.di.module

import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.core.interactors.NewsFeedInteractor
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.presenter.news.NewsFeedPresenter

@Module
class NewsFeedModule {

    @ScreenScope
    @Provides
    fun provideNewsFeedPresenter(
        newsFeedInteractor: NewsFeedInteractor
    ) : NewsFeedPresenter = NewsFeedPresenter(newsFeedInteractor)
}
