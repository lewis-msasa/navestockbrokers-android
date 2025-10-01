package com.nafepay.domain.exceptions

import com.google.gson.Gson
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

class ExceptionHandler {

    companion object {
        suspend fun getError(response: HttpResponse): GeneralError {

            response.bodyAsText()?.let {

                var gson = Gson()

                return gson.fromJson(it, GeneralError::class.java);
            }
            throw IllegalArgumentException("not a parsable error")
        }
    }
}