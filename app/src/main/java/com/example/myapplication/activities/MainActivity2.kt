package com.example.myapplication.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.myapplication.R
import com.example.myapplication.api.AadhaarKarzaApi
import com.example.myapplication.viewModel.MainViewModel
import com.google.gson.Gson
import com.karza.qrcodescansdk.CodeScanner
import com.karza.qrcodescansdk.CodeScannerView
import com.karza.qrcodescansdk.DecodeCallback
import com.karza.qrcodescansdk.KEnvironment
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "camera permission accepted", Toast.LENGTH_LONG).show();
            permissionGranted()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission accepted", Toast.LENGTH_LONG).show();
                permissionGranted()
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun permissionGranted() {
        val startNewActivityButton =
            findViewById<Button>(R.id.startNewActivityButton)
        startNewActivityButton.visibility = View.INVISIBLE
        val scannerView: CodeScannerView = findViewById(R.id.scannerView)
        val kEnv = KEnvironment.PRODUCTION;
        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://api.karza.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val xKarzaKey = ""
        val apiInterface = retrofitClient.create(AadhaarKarzaApi::class.java)
        val karzaTokenResponse = runBlocking {
            val body = mutableMapOf<String, Any?>()
            body["productId"] = listOf("aadhaar_xml")
            apiInterface.getKarzaToken(
                xKarzaKey = xKarzaKey,
                content_type = "application/json",
                body = body
            )
        }
        val karzaToken = karzaTokenResponse.result?.data?.karzaToken
        val email = ""
        val mobile = ""
        val caseId = ""
        if (karzaToken != null) {
            val codeScanner =
                CodeScanner(this, scannerView, kEnv, karzaToken, mobile, email, caseId)
            codeScanner.decodeCallback = DecodeCallback { result ->
                if (!result.isEmpty) {
                    if (result.getBoolean("isError")) {
                        codeScanner.releaseResources()
                        Toast.makeText(this, "some error occurred", Toast.LENGTH_LONG).show()
                    } else {
                        startNewActivityButton.visibility = View.VISIBLE
                        startNewActivityButton.setOnClickListener {
                            val intent =
                                Intent(this@MainActivity2, AadhaarQrResultActivity::class.java)
                            startActivity(intent)
                            val bundle = Bundle()
                            bundle.putString("bundle", Gson().toJson(result))
                            intent.putExtras(bundle)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            startNewActivityButton.isVisible = !startNewActivityButton.isVisible
                            codeScanner.startPreview()
                        }
                        codeScanner.releaseResources()
                    }
                } else {
                    codeScanner.releaseResources()
                    Toast.makeText(this, "Empty result received", Toast.LENGTH_LONG).show()
                }
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }
}