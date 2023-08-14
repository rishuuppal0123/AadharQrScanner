package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class KarzaApiResponse(
    @SerializedName("requestId") val requestId: String?,
    @SerializedName("result") val result: KarzaApiResult?
)

data class KarzaApiResult(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("reason") val reason: String?,
    @SerializedName("data") val data: KarzaTokenMap?
)

data class KarzaTokenMap(
    @SerializedName("karzaToken") val karzaToken: String?
)