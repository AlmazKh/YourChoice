package ru.itis.yourchoice.di.component

import dagger.Component
import ru.itis.yourchoice.di.module.AppModule
import ru.itis.yourchoice.di.module.InteractorModule
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.di.module.RepoModule
import ru.itis.yourchoice.view.login.LoginActivity
import ru.itis.yourchoice.view.login.LoginWithPhoneDialog
import javax.inject.Singleton

@Component(
    modules = [
        PresenterModule::class,
        AppModule::class,
        RepoModule::class,
        InteractorModule::class
    ]
)
@Singleton
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(loginWithPhoneDialog: LoginWithPhoneDialog)
//    fun inject(menuFragment: MenuFragment)
//    fun inject(addPostFragment: AddPostFragment)
//    fun inject(newsFragment: NewsFragment)
}
