package ru.itis.yourchoice

import android.app.Application
import ru.itis.yourchoice.di.component.AppComponent
import ru.itis.yourchoice.di.component.AuthComponent
import ru.itis.yourchoice.di.component.DaggerAppComponent
import ru.itis.yourchoice.di.module.AppModule

class YourChoiceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}