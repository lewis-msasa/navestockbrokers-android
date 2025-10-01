package com.nafepay.domain.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "users")
@Serializable
data class User(
    @PrimaryKey val id: String,
    val username: String,
    val email: String
)