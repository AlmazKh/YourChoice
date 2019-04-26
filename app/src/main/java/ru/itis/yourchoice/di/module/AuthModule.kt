package ru.itis.yourchoice.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.R
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.core.model.ResHolder
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.presenter.login.LoginActivityPresenter
import ru.itis.yourchoice.presenter.login.LoginWithPhoneDialogPresenter

@Module
class AuthModule {

    @ScreenScope
    @Provides
    fun provideLoginActivityPresenter(
        loginInteractor: LoginInteractor,
        googleSignInClient: GoogleSignInClient
    ): LoginActivityPresenter =
        LoginActivityPresenter(loginInteractor, googleSignInClient)

    @ScreenScope
    @Provides
    fun provideLoginWithPhoneDialogPresenter(
        loginInteractor: LoginInteractor
    ): LoginWithPhoneDialogPresenter =
        LoginWithPhoneDialogPresenter(loginInteractor)

    @ScreenScope
    @Provides
    fun provideSignInOptions(resHolder: ResHolder): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resHolder.resources.getString(R.string.web_client_id))
            .requestEmail()
            .build()
    }

    @ScreenScope
    @Provides
    fun provideGoogleSignInClient(activity: AppCompatActivity, options: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(activity, options)
    }

    @ScreenScope
    @Provides
    fun provideResources(context: Context): ResHolder {
        return ResHolder(context.resources)
    }
}