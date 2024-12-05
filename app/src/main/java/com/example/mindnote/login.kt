package com.example.mindnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.btn_login)
// Set OnClickListener untuk berpindah ke Register Screen
        button.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this,
                loginsukses::class.java
            )
            startActivity(intent)
        }
        val textsignin = findViewById<TextView>(R.id.txt_createacc)
// Set OnClickListener untuk berpindah ke Register Screen
        textsignin.setOnClickListener { // Intent untuk berpindah ke RegisterActivity
            val intent: Intent = Intent(
                this,
                createaccount::class.java
            )
            startActivity(intent)
        }
    }
}