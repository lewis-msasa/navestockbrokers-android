package com.nafepay.common_ui.models

import com.nafepay.domain.database.models.User
import com.nafepay.domain.models.authentication.user.UserDTO

data class UserModel(
    val name: String,
    val email : String,
    val id: String,
){
    object ModelMapper {
        fun fromUser(user : User, ) =
            UserModel(
                name = user.name,
                email = user.email,
                id = user.id,

            )
        fun fromUserDTO(user : UserDTO, ) =
            UserModel(
                name = user.fullName,
                email = user.email,
                id = user.id

            )
//        fun fromGraph(user : GetUserQuery.Data) =
//            UserModel(
//                name = user.user!!.fullName,
//                email = user.user!!.email,
//                profile = user.user!!.profile,
//                profilePic = user.user!!.profilePicPath,
//                id = user.user!!.id,
//            )
    }
}
