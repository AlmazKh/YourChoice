package ru.itis.yourchoice.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.presenter.LoginActivityPresenter

@Module
class PresenterModule(private val context: Context) {

    @Provides
    fun provideLoginActivityPresenter(): LoginActivityPresenter = LoginActivityPresenter()
}