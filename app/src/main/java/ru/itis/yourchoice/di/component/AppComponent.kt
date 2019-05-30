package ru.itis.yourchoice.di.component

import dagger.Component
import ru.itis.yourchoice.di.module.AppModule
import ru.itis.yourchoice.di.module.DbModule
import ru.itis.yourchoice.di.module.RepoModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepoModule::class,
        AppModule::class,
        DbModule::class
    ]
)
interface AppComponent {
    fun authComponent(): AuthComponent.Builder
    fun appPostComponent(): AddPostComponent.Builder
    fun newsFeedComponent(): NewsFeedComponent.Builder
    fun menuComponent(): MenuComponent.Builder
}
