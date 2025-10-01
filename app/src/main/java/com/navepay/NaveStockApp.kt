package com.navepay

import android.app.Application
import com.nafepay.domain.di.appModule
import com.nafepay.domain.di.databaseModule
import com.nafepay.domain.di.networkModule
import com.nafepay.domain.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
class NaveStockApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NaveStockApp)
            modules(listOf(appModule, networkModule, repositoryModule, databaseModule))
        }
    }
}