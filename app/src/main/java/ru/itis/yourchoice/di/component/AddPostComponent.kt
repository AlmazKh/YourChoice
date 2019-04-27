package ru.itis.yourchoice.di.component

import android.support.v7.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.yourchoice.di.module.AddPostModule
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.view.addpost.AddPostFragment

@Subcomponent(
        modules = [
            AddPostModule::class
        ]
)
@ScreenScope
interface AddPostComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): AddPostComponent
    }

    fun inject(addPostFragment: AddPostFragment)
}
