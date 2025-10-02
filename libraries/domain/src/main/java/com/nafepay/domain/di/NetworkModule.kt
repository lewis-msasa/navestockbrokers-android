package com.nafepay.domain.di

import androidx.room.Room
import com.nafepay.domain.repositories.authentication.AuthenticationRepository
import com.nafepay.core.di.Preferences
import com.nafepay.domain.database.AppDatabase
import com.nafepay.domain.interactors.authentication.Authenticate
import com.nafepay.domain.remote.AuthInterceptor
import com.nafepay.domain.remote.authentication.AuthenticationKtorService
import com.nafepay.domain.remote.authentication.AuthenticationRemote
import com.nafepay.domain.remote.authentication.AuthenticationRemoteKtorStore
import com.nafepay.domain.remote.ktor.KtorClient
import com.nafepay.domain.repositories.authentication.AuthenticationDataRepository
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module{
    single { Preferences(androidContext()) }
}
val networkModule = module {


    single { AuthInterceptor(get()) }
    single {
        KtorClient.getClient(get())
        //KtorMockClient.ktorHttpClient
    }

    single{ AuthenticationKtorService(get()) }
    single<AuthenticationRemote>{ AuthenticationRemoteKtorStore(get()) }

    single { Authenticate(get(), get()) }
}
val repositoryModule = module {
    single<AuthenticationRepository>{ AuthenticationDataRepository(get()) }

}
val databaseModule = module {

    // Provide Room database singleton
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    // Provide DAOs
    single { get<AppDatabase>().userDao() }
}