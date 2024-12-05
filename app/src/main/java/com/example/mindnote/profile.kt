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

class profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imagebook = findViewById<ImageView>(R.id.img_book)
// Set OnClickListener untuk berpindah ke Register Screen
        imagebook.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@profile,
                penulisan::class.java
            )
            startActivity(intent)
        }
        val imagekalender = findViewById<ImageView>(R.id.img_kalender)
// Set OnClickListener untuk berpindah ke Register Screen
        imagekalender.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@profile,
                kalender::class.java
            )
            startActivity(intent)
        }
        val buttonlogout = findViewById<Button>(R.id.btn_logout)
// Set OnClickListener untuk berpindah ke Register Screen
        buttonlogout.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this@profile,
                login::class.java
            )
            startActivity(intent)
        }
    }
}