package ru.itis.yourchoice.di.module

import android.content.Context
import android.support.annotation.Nullable
import dagger.Module
import dagger.Provides
import ru.itis.yourchoice.data.db.AppDb
import ru.itis.yourchoice.data.db.PostDataDao

@Module
class DbModule(
    private val context: Context
) {
    @Provides
    @Nullable
    fun provideDb(): PostDataDao? = AppDb.getInstance(context)?.postDataDao()
}
