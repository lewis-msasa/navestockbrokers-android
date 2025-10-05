package com.nafepay.domain.remote.mock.authentication.login

import com.nafepay.domain.database.models.User
import com.nafepay.domain.models.authentication.login.SigninResult
import com.nafepay.domain.models.authentication.registration.SignupResult
import com.google.gson.Gson
import com.nafepay.domain.models.GeneralResult


object LogoutMockResponse{
    operator fun invoke(): String {
        val response = GeneralResult(
            success = true,
            message = "successfully logged out"
        )
        return Gson().toJson(response)
    }
}
object LoginMockResponse{
    operator fun invoke(): String {
        val response = SigninResult(
            success = true,
            token = "dsfsdafdsgsdfgefgrgdfbvsfdfgfdgsdfs",
            refreshToken = "sdfsadfdsgfdgdfgf",
            user = User(
                name = "Lewis Msasa",
                id = "sdfsdfsdf",
                email = "lmsasajnr@d.com",
                username = "lmsasajnr@gmail.com"
            )
        )
        return Gson().toJson(response)
    }
}
object SocialLoginMockResponse{
    operator fun invoke(): String {
        val response = SigninResult(
            success = true,
            token = "dsfsdafdsgsdfgefgrgdfbvsfdfgfdgsdfs",
            refreshToken = "sdfsadfdsgfdgdfgf",
            user = User(
                name = "Lewis Msasa",
                id = "sdfsdfsdf",
                email = "lmsasajnr@d.com",
                username = "lmsasajnr@d.com"
            )
        )
        return Gson().toJson(response)
    }
}