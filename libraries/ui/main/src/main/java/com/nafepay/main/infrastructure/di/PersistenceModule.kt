package com.nafepay.main.infrastructure.di

import android.app.Application
import androidx.room.Room
import com.nafepay.main.infrastructure.persistence.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val databaseModule = module {
    single {
        AppDatabase.createDatabase(
            get(), // Application injected by Koin via androidContext()
            get<Application>().getString(com.nafepay.common_ui.R.string.database)
        )
    }

    // Expose DAOs if needed
    single { get<AppDatabase>().userDao() }
    // single { get<AppDatabase>().otherDao() }
}