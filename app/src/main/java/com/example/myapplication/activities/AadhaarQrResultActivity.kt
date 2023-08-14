package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class AadhaarQrResultActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrresultactivity)
        val extras = intent.extras
        if (extras != null) {
            val bundleData = extras.getString("bundle")

            val editText = findViewById<EditText>(R.id.editText)
            editText.setText(bundleData)
            val copyButton = findViewById<Button>(R.id.copyButton)
            copyButton.setOnClickListener {
                if(bundleData != null) {
                    copyText(bundleData)
                }
            }
        }
    }

    private fun copyText(text: String) {
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Text copied", Toast.LENGTH_SHORT).show()
    }

//    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
//    override fun onBackPressed() {
//        finish()
//    }

}