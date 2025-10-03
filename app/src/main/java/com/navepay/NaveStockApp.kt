package com.navepay

import android.app.Application
import com.nafepay.domain.database.models.User
import com.nafepay.main.infrastructure.di.databaseModule
import com.nafepay.main.infrastructure.di.networkModule
import com.nafepay.main.infrastructure.di.repositoryModule
import com.navepay.di.appModule
import com.navepay.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
class NaveStockApp : Application() {
    var user : User? = null
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NaveStockApp)
            modules(listOf( networkModule, repositoryModule, databaseModule,
                appModule,viewModelModules
            ))
        }
    }
}