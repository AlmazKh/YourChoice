package ru.itis.yourchoice.data.db

import android.arch.persistence.room.*
import io.reactivex.Single
import ru.itis.yourchoice.core.model.PostRemote

@Dao
interface PostDataDao {
    @Query("SELECT * FROM post_remote")
    fun getAll(): Single<MutableList<PostRemote>>

    @Query("DELETE FROM post_remote")
    fun nukeTable()

    @Insert
    fun insert(post: PostRemote)

    @Insert
    fun insert(posts: List<PostRemote>)

    @Update
    fun update(post: PostRemote)

    @Delete
    fun delete(post: PostRemote)
}
