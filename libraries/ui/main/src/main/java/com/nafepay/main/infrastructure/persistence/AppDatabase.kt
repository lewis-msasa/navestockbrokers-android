package com.nafepay.main.infrastructure.persistence

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.nafepay.domain.database.daos.*
import com.nafepay.domain.database.models.*

@Database(entities = [User::class],
    version = 1,
    exportSchema = true,
    /*autoMigrations = [
        AutoMigration (
            from = 6,
            to = 7,
            spec = AppDatabase.MyAutoMigration::class
        )
    ]*/
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao


    @DeleteColumn(tableName =  "User", columnName =  "userName")
    class MyAutoMigration : AutoMigrationSpec {

    }

    companion object {
        fun createDatabase(appContext: Context, databaseName : String): AppDatabase {
            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                databaseName
            )
            //.fallbackToDestructiveMigration()
            //.addMigrations(MIGRATION_3_4, MIGRATION_4_5)
            .build()
        }
    }
}