package com.example.mindnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class profile : AppCompatActivity() {  // Nama class diperbaiki (huruf kapital)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Atur padding agar tidak bertabrakan dengan sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi view
        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        val imgBook = findViewById<ImageView>(R.id.img_book)
        val imgKalender = findViewById<ImageView>(R.id.img_kalender)
        val imgAccount = findViewById<ImageView>(R.id.img_account) // Tambah event untuk profil

        // Ambil data yang tersimpan di SharedPreferences
        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        etName.setText(sharedPref.getString("name", ""))
        etEmail.setText(sharedPref.getString("email", ""))

        // Tombol Simpan (Save Data ke SharedPreferences) dengan validasi
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Nama dan email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                sharedPref.edit().apply {
                    putString("name", name)
                    putString("email", email)
                    apply()
                }
                Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigasi ke halaman lain
        imgBook.setOnClickListener {
            startActivity(Intent(this, Penulisan::class.java))
        }

        imgKalender.setOnClickListener {
            startActivity(Intent(this, Kalender::class.java))
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        // Event saat ikon profil ditekan (biar gak crash)
        imgAccount.setOnClickListener {
            Toast.makeText(this, "Kamu sedang di halaman profil!", Toast.LENGTH_SHORT).show()
        }
    }
}