package ru.itis.yourchoice.di.component

import dagger.Component
import ru.itis.yourchoice.di.module.AppModule
import ru.itis.yourchoice.di.module.RepoModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepoModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun authComponent(): AuthComponent.Builder
}