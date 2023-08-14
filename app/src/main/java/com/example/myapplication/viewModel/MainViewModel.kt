package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repo.AadhaarQrRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repo: AadhaarQrRepo) : ViewModel() {
    fun getKarzaToken() {
        val body = mutableMapOf<String, Any?>()
        body["productId"] = listOf("aadhaar_xml")
        val contentType = "application/json"
        val xKarzaKey = "1Vyb1DnuX2pdTpA"
        viewModelScope.launch {
            when (val response =
                repo.getKarzaToken(contentType = contentType, xKarzaKey = xKarzaKey, body = body)) {

            }
        }
    }
}