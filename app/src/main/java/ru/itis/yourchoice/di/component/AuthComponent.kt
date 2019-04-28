package ru.itis.yourchoice.di.component

import android.support.v7.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.yourchoice.di.module.AuthModule
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.view.login.LoginActivity
import ru.itis.yourchoice.view.login.LoginWithPhoneDialog

@Subcomponent   (
    modules = [
        AuthModule::class
    ]
)
@ScreenScope
interface AuthComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): AuthComponent
    }

    fun inject(loginActivity: LoginActivity)
    fun inject(loginWithPhoneDialog: LoginWithPhoneDialog)
}