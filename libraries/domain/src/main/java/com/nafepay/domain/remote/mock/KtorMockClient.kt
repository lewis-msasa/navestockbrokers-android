package com.nafepay.domain.remote.mock

import com.nafepay.domain.remote.mock.authentication.login.LoginMockResponse
import com.nafepay.domain.remote.mock.authentication.login.LogoutMockResponse
import com.nafepay.domain.remote.mock.authentication.registration.GeneralMockResponse
import com.nafepay.domain.remote.mock.authentication.registration.SignUpMockResponse
import com.nafepay.domain.remote.mock.authentication.users.UserNotificationsMockResponse
import com.nafepay.domain.remote.mock.authentication.users.UserNumNotificationsMockResponse
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.text.get
import io.ktor.client.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*



class KtorMockClient {
    companion object {
        private const val TIME_OUT = 60_000

        val ktorHttpClient = HttpClient(MockEngine) {
            engine {
                /*addHandler { request ->
                    error("Unhandled ${request.url.encodedPath}")
                }*/
                addHandler { request ->
                    when (request.url.encodedPath) {
                       
                        "/User/signup" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(SignUpMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/User/socialSignin" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/User/logout" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LogoutMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/reset" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/signin" -> {

                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(LoginMockResponse(), HttpStatusCode.OK, responseHeaders)

                        }
                        "/User/forgotPassword" -> {
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }


                        "/UserCode/verifyUserCode" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/passwordchange" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/UserCode/resendUserCode" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/deleteAccount" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/disableAccount" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/DeviceToken/saveToken" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(GeneralMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }

                       

                        "/User/notifications/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/users/User/numNotifications/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNumNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        "/User/notifications/unread/sdfsdfsdf" ->{
                            val responseHeaders = headersOf("Content-Type"
                                    to listOf(ContentType.Application.Json.toString()))
                            respond(UserNotificationsMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                        else ->{
                            error("Unhandled ${request.url.encodedPath}")
                        }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}