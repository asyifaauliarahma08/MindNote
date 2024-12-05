package com.example.mindnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class catatanku : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catatanku)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imagecatatan = findViewById<ImageView>(R.id.img_catatan)
// Set OnClickListener untuk berpindah ke Register Screen
        imagecatatan.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@catatanku,
                penulisan::class.java
            )
            startActivity(intent)
        }

        val imagekalender = findViewById<ImageView>(R.id.img_kekalender)
// Set OnClickListener untuk berpindah ke Register Screen
        imagekalender.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this,
                kalender::class.java
            )
            startActivity(intent)
        }

        val imageacc = findViewById<ImageView>(R.id.img_account)
// Set OnClickListener untuk berpindah ke Register Screen
        imageacc.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@catatanku,
                profile::class.java
            )
            startActivity(intent)
        }
    }
}