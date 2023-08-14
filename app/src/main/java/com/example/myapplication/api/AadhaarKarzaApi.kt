package com.example.myapplication.api

import com.example.myapplication.model.KarzaApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AadhaarKarzaApi {

    @POST("v3/get-jwt")
    suspend fun getKarzaToken(
        @Header("Content-Type") content_type: String,
        @Header("x-karza-key") xKarzaKey: String,
        @Body body: MutableMap<String, Any?>
    ): KarzaApiResponse
}