package com.nafepay.core.utils

import android.text.TextUtils
import android.util.Patterns

object Validation {

    fun isEmail(email : String) : Boolean{
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    fun isValidPassword(password : String) : Boolean{
        //val regex = "^.*(?=.{6,})((?=.*[!@#\$%^&*()\\-_=+{};:,<.>]){1})(?=.*\\d)((?=.*[a-z]){1}).*\$".toRegex()
        val regex = """^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&#]).{8,}$""".toRegex()
        return regex.matches(password);
    }
    fun isValidMalawiPhoneNumber(phone: String): Boolean {
        // Remove spaces, dashes, parentheses
        val cleaned = phone.replace("""[\s()-]""".toRegex(), "")

        // Regex for local format: starts with 0, then 9 digits
        val localRegex = """^0(8[0-9]|9[0-9])\d{7}$""".toRegex()

        // Regex for international format: +265, then 9 digits
        val intlRegex = """^\+265(8[0-9]|9[0-9])\d{7}$""".toRegex()

        return localRegex.matches(cleaned) || intlRegex.matches(cleaned)
    }

    fun isTokenValid(token : String) : Boolean{
        return true;
    }
}