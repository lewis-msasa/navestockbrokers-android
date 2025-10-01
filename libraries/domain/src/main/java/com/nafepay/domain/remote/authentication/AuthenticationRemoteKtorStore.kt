package com.nafepay.domain.remote.authentication


import com.nafepay.domain.models.GeneralResult
import com.nafepay.domain.models.authentication.login.RefreshTokenRequest
import com.nafepay.domain.models.authentication.login.RefreshTokenResult
import com.nafepay.domain.models.authentication.login.SigninRequest
import com.nafepay.domain.models.authentication.login.SigninResult
import com.nafepay.domain.models.authentication.login.SocialMediaLoginRequest
import com.nafepay.domain.models.authentication.password.PasswordChangeRequest
import com.nafepay.domain.models.authentication.password.ResetPasswordRequest
import com.nafepay.domain.models.authentication.registration.SignupRequest
import com.nafepay.domain.models.authentication.registration.SignupResult
import com.nafepay.domain.models.authentication.registration.VerifyCodeRequest
import com.nafepay.domain.models.authentication.user.DeleteAccountDTO
import com.nafepay.domain.models.authentication.user.DisableAccountDTO
import com.nafepay.domain.models.notifications.NotificationsResult

class AuthenticationRemoteKtorStore constructor(
    private val authenticationService: AuthenticationKtorService,
) : AuthenticationRemote {
    override suspend fun signUp(
        userName: String,
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
    ): SignupResult? {
        return authenticationService.signUp(
            SignupRequest(
                userName = userName,
                fullName = fullName,
                email = email,
                password = password,
                phoneNumber = phoneNumber,
            )
        )
    }

    override suspend fun socialSignIn(
        fullName: String,
        username: String,
        emailAddress: String,
        service: String,
        token:String,
        profileImageUrl : String?,
        isFirstTime : Boolean
    ): SigninResult? {
        return authenticationService.socialSignin(
            SocialMediaLoginRequest(
                userName = username,
                email = emailAddress,
                service = service,
                fullName = fullName,
                token = token,
                isFirstTime = isFirstTime,
                profileImageUrl = profileImageUrl,
            )
        )
    }

    override suspend fun signIn(
        emailAddress: String,
        password: String,
        restore: Boolean
    ): SigninResult? {
        return authenticationService.signIn(
            SigninRequest(
                username = emailAddress,
                password = password
            ),
            restore
        )
    }



    override suspend fun logout() {
        authenticationService.logout()
    }

    override suspend fun forgotPassword(email: String): GeneralResult? {
        return authenticationService.forgotPassword(email)
    }

    override suspend fun resendCode(userId: String, isRestore: Boolean): GeneralResult? {
       return authenticationService.resendCode(userId,isRestore)
    }



    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String,
        restore: Boolean
    ): SigninResult? {
       return  authenticationService.resetPassword(
           ResetPasswordRequest(
               email = email,
               code = code,
               password = password
           )
       )
    }

    override suspend fun changePassword(
        username: String,
        newPassword: String,
        oldPassword: String
    ): GeneralResult? {

        return authenticationService.changePassword(
            PasswordChangeRequest(
                username = username,
                newPassword = newPassword,
                oldPassword = oldPassword
            )
        )
    }

    override suspend fun verifyUserCode(userId: String, code: String): GeneralResult? {

            return authenticationService.verifyUserCode(
                VerifyCodeRequest(
                    userId = userId,
                    code = code
                )
            )

    }

    override suspend fun refreshToken(token: String, refreshToken: String): RefreshTokenResult? {
       return authenticationService.refreshToken(
           RefreshTokenRequest(
               token = token,
               refreshToken = refreshToken
           )
       )
    }

    override suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean): NotificationsResult? {
        return authenticationService.getUserNotifications(userId,page, unread)
    }

    override suspend fun getNumberUnreadNotifications(id: String): Int {
        return authenticationService.getNumberUnreadNotifications(id)
    }

    override suspend fun deleteAccount(delete: DeleteAccountDTO) = authenticationService.deleteAccount(delete)
    override suspend fun disableAccount(disable: DisableAccountDTO) = authenticationService.disableAccount(disable)
    override suspend fun sendDeviceToken(token: String): GeneralResult? = authenticationService.sendDeviceToken(token)
}