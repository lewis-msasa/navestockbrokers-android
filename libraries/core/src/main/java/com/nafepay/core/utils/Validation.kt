package com.nafepay.core.utils

import android.text.TextUtils
import android.util.Patterns

object Validation {

    fun isEmail(email : String) : Boolean{
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    fun isValidPassword(password : String) : Boolean{
        val regex = "^.*(?=.{6,})((?=.*[!@#\$%^&*()\\-_=+{};:,<.>]){1})(?=.*\\d)((?=.*[a-z]){1}).*\$".toRegex()
        return regex.matches(password);
    }

    fun isTokenValid(token : String) : Boolean{
        return true;
    }
}