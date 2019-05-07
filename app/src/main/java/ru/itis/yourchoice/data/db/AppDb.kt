package ru.itis.yourchoice.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.itis.yourchoice.core.model.Post

private const val DB_NAME: String = "YC.db"

@Database(entities = [Post::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun postDataDao(): PostDataDao

    companion object {
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb? {
            if (INSTANCE == null) {
                synchronized(AppDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDb::class.java, DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}