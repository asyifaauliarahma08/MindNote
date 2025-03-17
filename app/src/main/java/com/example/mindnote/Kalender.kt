package com.example.mindnote

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Kalender : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imagebook = findViewById<ImageView>(R.id.img_book)
// Set OnClickListener untuk berpindah ke Register Screen
        imagebook.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@Kalender,
                catatanku::class.java
            )
            startActivity(intent)
        }
        val imageaccount = findViewById<ImageView>(R.id.img_account)
// Set OnClickListener untuk berpindah ke Register Screen
        imageaccount.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@Kalender,
                profile::class.java
            )
            startActivity(intent)
        }
    }
}