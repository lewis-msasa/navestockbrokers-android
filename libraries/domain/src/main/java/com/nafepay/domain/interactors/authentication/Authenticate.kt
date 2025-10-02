package com.nafepay.domain.interactors.authentication



import com.nafepay.domain.repositories.authentication.AuthenticationRepository
import com.nafepay.domain.database.daos.UserDao
import com.nafepay.domain.database.models.User
import com.nafepay.domain.models.authentication.user.DeleteAccountDTO
import com.nafepay.domain.models.authentication.user.DisableAccountDTO
import com.nafepay.domain.models.authentication.user.EditUserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class Authenticate(
    private val authenticationRepository: AuthenticationRepository,
    private val userDao: UserDao
){
      suspend fun getCurrentUser(getUser : (user : User) -> Unit) {
          withContext(Dispatchers.IO) {
              userDao.getUser().collect {
                  getUser(it)
              }
          }
      }
      suspend fun sendDeviceToken(token : String) = authenticationRepository.sendDeviceToken(token)
      suspend fun signUp(
         userName: String,
         fullName: String,
         email: String,
         password: String,
         phoneNumber: String,
     ) = authenticationRepository.signUp(
         userName,
         fullName,
         email,
         password,
         phoneNumber,
     )

      suspend fun signIn(
         emailAddress: String,
         password: String,
         restore: Boolean
     ) = authenticationRepository.signIn(
         emailAddress,
         password,
         restore
     )

     suspend fun socialSignIn(
         fullName: String,
         username: String,
         emailAddress: String,
         service: String,
         token : String,
         profileImageUrl : String?,
         isFirstTime: Boolean
     ) = authenticationRepository.socialSignIn(
          fullName = fullName,
          username = username,
          emailAddress = emailAddress,
          service = service,
          token = token,
          profileImageUrl = profileImageUrl,
          isFirstTime = isFirstTime

     )


      suspend fun logout() = authenticationRepository.logout()

      suspend fun forgotPassword(email: String) = authenticationRepository.forgotPassword(email)

     suspend fun resendCode(userId: String, isReset : Boolean) = authenticationRepository.resendCode(userId,isReset)

      suspend fun resetPassword(
         email: String,
         code: String,
         password: String,
         restore:Boolean
     ) = authenticationRepository.resetPassword(
         email,
         code,
         password,
          restore
     )

      suspend fun changePassword(
         username: String,
         newPassword: String,
         oldPassword: String
     ) = authenticationRepository.changePassword(
         username,
         newPassword,
         oldPassword
     )

      suspend fun verifyUserCode(userId: String, code: String)
             = withContext(Dispatchers.IO) {
          authenticationRepository.verifyUserCode(userId,code)
      }

      suspend fun refreshToken(token: String, refreshToken: String)
             = withContext(Dispatchers.IO) {
          authenticationRepository.refreshToken(token,refreshToken)
      }



     suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean) = withContext(Dispatchers.IO) {
         authenticationRepository.getUserNotifications(userId,page, unread)
     }



    suspend fun getNumberUnreadNotifications(id: String) = withContext(Dispatchers.IO){
        authenticationRepository.getNumberUnreadNotifications(id)
    }

    suspend  fun editUser(user: EditUserDTO) {

    }
    suspend  fun deleteAccount(delete: DeleteAccountDTO)  = authenticationRepository.deleteAccount(delete)
    suspend fun disableAccount(disable: DisableAccountDTO) = authenticationRepository.disableAccount(disable)


}