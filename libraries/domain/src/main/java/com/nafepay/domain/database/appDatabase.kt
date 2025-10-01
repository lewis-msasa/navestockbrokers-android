package com.nafepay.domain.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.nafepay.domain.database.daos.UserDao
import com.nafepay.domain.database.models.User

@Database(
    entities = [User::class], // add your entities here
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}
