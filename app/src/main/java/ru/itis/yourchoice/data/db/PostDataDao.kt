package ru.itis.yourchoice.data.db

import android.arch.persistence.room.*
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Post

@Dao
interface PostDataDao {
    @Query("SELECT * FROM post")
    fun getAll(): Single<MutableList<Post>>

    @Query("SELECT * FROM post WHERE id = :id")
    fun getById(id: Int): Single<Post>

    @Query("DELETE FROM post")
    fun nukeTable()

    @Insert
    fun insert(post: Post)

    @Insert
    fun insert(posts: List<Post>)

    @Update
    fun update(post: Post)

    @Delete
    fun delete(post: Post)
}
