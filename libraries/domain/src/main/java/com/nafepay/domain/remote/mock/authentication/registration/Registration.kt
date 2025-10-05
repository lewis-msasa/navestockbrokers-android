package com.nafepay.domain.remote.mock.authentication.registration


import com.nafepay.domain.database.models.User
import com.nafepay.domain.models.authentication.registration.SignupResult
import com.nafepay.domain.models.authentication.registration.UserResult
import com.google.gson.Gson
import com.nafepay.domain.models.GeneralResult

object SignUpMockResponse{
    operator fun invoke(): String {
        val response = SignupResult(
            success = true,
            token = "dsfsdafdsgsdfgefgrgdfbvsfdfgfdgsdfs",
            refreshToken = "sdfsadfdsgfdgdfgf",
            user = UserResult(
               name = "Lewis Msasa",
               privateKey = "123432w343",
               publicKey = "44322433",
               profilePicPath = "",
               id = "sdfsdfsdf",
               email = "lmsasajnr@d.com",
            )
        )
        return Gson().toJson(response)
    }
}

object GeneralMockResponse{
    operator fun invoke() : String {
        val response = GeneralResult(
            success = true,
            message = "Successfully Done"
        )
        return Gson().toJson(response)
    }
}
