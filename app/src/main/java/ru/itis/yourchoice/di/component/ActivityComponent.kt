package ru.itis.yourchoice.di.component

import dagger.Component
import ru.itis.yourchoice.di.module.AppModule
import ru.itis.yourchoice.di.module.InteractorModule
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.di.module.RepoModule
import ru.itis.yourchoice.view.AddPostFragment
import ru.itis.yourchoice.view.LoginActivity
import ru.itis.yourchoice.view.MenuFragment
import ru.itis.yourchoice.view.NewsFragment
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
//    fun inject(menuFragment: MenuFragment)
//    fun inject(addPostFragment: AddPostFragment)
//    fun inject(newsFragment: NewsFragment)
}