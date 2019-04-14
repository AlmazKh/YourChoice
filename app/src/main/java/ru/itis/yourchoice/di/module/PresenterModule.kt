package ru.itis.yourchoice.di.module

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.presenter.login.LoginActivityPresenter
import ru.itis.yourchoice.presenter.login.LoginWithPhoneDialogPresenter
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun provideLoginActivityPresenter(
       loginInteractor: LoginInteractor,
       firebaseAuth: FirebaseAuth
    ): LoginActivityPresenter =
        LoginActivityPresenter(loginInteractor, firebaseAuth)

    @Singleton
    @Provides
    fun provideLoginWithPhoneDialogPresenter(
        loginInteractor: LoginInteractor
    ): LoginWithPhoneDialogPresenter =
        LoginWithPhoneDialogPresenter(loginInteractor)
}