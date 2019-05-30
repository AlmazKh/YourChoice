package ru.itis.yourchoice.di.component

import android.support.v7.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.yourchoice.di.module.NewsFeedModule
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.view.news.NewsFeedFragment
import ru.itis.yourchoice.view.news.PostFragment

@Subcomponent(
    modules = [
        NewsFeedModule::class
    ]
)
@ScreenScope
interface NewsFeedComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): NewsFeedComponent
    }

    fun inject(newsFeedFragment: NewsFeedFragment)
    fun inject(postFragment: PostFragment)
}
