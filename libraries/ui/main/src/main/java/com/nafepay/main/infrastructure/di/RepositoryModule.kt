package com.nafepay.main.infrastructure.di

import com.nafepay.domain.repositories.authentication.AuthenticationDataRepository
import com.nafepay.domain.repositories.authentication.AuthenticationRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<AuthenticationRepository>{ AuthenticationDataRepository(get()) }

}