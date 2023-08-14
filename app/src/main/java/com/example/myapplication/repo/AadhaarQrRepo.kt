package com.example.myapplication.repo

interface AadhaarQrRepo {
    suspend fun getKarzaToken(contentType: String, xKarzaKey: String, body: MutableMap<String, Any?>) : String
}