package com.nafepay.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nafepay.domain.database.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM users")
    fun getUser(): Flow<User>

    //@Query("SELECT * FROM User WHERE id = :id")
    //fun getUserById(id: String): Flow<User>

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAll() : Int

    @Delete
    suspend fun delete(user: User) : Int
}