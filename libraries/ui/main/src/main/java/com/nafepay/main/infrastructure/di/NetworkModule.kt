package com.nafepay.main.infrastructure.di

import com.nafepay.domain.interactors.authentication.Authenticate
import com.nafepay.domain.remote.AuthInterceptor
import com.nafepay.domain.remote.authentication.AuthenticationKtorService
import com.nafepay.domain.remote.authentication.AuthenticationRemote
import com.nafepay.domain.remote.authentication.AuthenticationRemoteKtorStore
import com.nafepay.domain.remote.ktor.KtorClient
import com.nafepay.domain.repositories.authentication.AuthenticationDataRepository
import com.nafepay.domain.repositories.authentication.AuthenticationRepository
import org.koin.dsl.module

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

