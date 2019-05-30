package ru.itis.yourchoice.di.component

import android.support.v7.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.itis.yourchoice.di.module.MenuModule
import ru.itis.yourchoice.di.scope.ScreenScope
import ru.itis.yourchoice.view.menu.interests.InterestsFragment
import ru.itis.yourchoice.view.menu.MenuFragment
import ru.itis.yourchoice.view.menu.help.HelpFragment
import ru.itis.yourchoice.view.menu.likes.LikesFragment
import ru.itis.yourchoice.view.menu.profile.UserProfileFragment
import ru.itis.yourchoice.view.menu.settings.SettingsFragment

@Subcomponent(
        modules = [
            MenuModule::class
        ]
)
@ScreenScope
interface MenuComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): MenuComponent
    }

    fun inject(menuFragment: MenuFragment)
    fun inject(interestsFragment: InterestsFragment)
    fun inject(userProfileFragment: UserProfileFragment)
    fun inject(likesFragment: LikesFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(helpFragment: HelpFragment)
}