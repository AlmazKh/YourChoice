package ru.itis.yourchoice.di.component

import dagger.Component
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.view.LoginActivity

@Component(
    modules = [
        PresenterModule::class,
        AppModule::class,
        RepoModule::class
    ]
)
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
}