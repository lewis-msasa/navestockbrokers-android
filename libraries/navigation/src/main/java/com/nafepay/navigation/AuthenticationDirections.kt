package com.nafepay.navigation

import androidx.navigation.NamedNavArgument

object AuthenticationDirections {
    val Default = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }
    val onboarding = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.ONBOARDING
    }

    val login = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.LOGIN
    }
    val verifyAccount = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.VERIFY_ACCOUNT
    }
    val registration = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.REGISTRATION
    }
    val mainTab = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.MAINTAB
    }
    val forgotPassword = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.FORGOTPASSWORD
    }
    val resetPassword = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = RouteConstants.Companion.RESETPASSWORD
    }
}