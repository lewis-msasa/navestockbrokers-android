package com.nafepay.domain.remote.apollo

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloSetup {
    fun setUpApolloClient(url: String, token: String = ""): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
        //.addInterceptor(MockInterceptor())
        if (token.isNotEmpty()) {
            okHttp.addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                builder.header("Authorization", "Bearer $token")
                chain.proceed(builder.build())
            }
        }



        var builder = ApolloClient
            .Builder()
            .serverUrl("https://${BuildConfig.FOV_URL}${url}")
            .okHttpClient(okHttp.build())
        return builder.build();
    }
    fun setUpTestApolloClient(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(MockInterceptor())


        return ApolloClient.Builder()
            .serverUrl("https://graphqlzero.almansi.me/api")
            .okHttpClient(okHttp.build())
            .build();
    }
}