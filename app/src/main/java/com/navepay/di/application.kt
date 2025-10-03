package com.navepay.di

import androidx.room.Room
import android.app.Application
import com.nafepay.authentication.viewModels.LoginViewModel
import com.nafepay.authentication.viewModels.PasswordViewModel
import com.nafepay.authentication.viewModels.RegistrationViewModel
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.core.di.Preferences
import com.nafepay.main.viewModels.MainViewModel
import com.nafepay.navigation.NavigationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { NavigationManager() }
    single { Preferences(androidContext()) }
}

val viewModelModules = module {
    viewModel {
        CommonViewModel(
            get<Application>(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        RegistrationViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        MainViewModel(
            get(),
            get()
        )
    }
    viewModel {
        LoginViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        PasswordViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        UsersViewModel(
            get(),
            get(),
            get(),
            get()
        )

    }
}
