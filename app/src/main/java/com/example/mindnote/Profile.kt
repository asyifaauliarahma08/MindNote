package com.example.mindnote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, Login::class.java))
            finish()
            return
        }

        val etName = findViewById<EditText>(R.id.et_name)
        val etAge = findViewById<EditText>(R.id.et_age)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        val userId = currentUser.uid
        val userDocRef = firestore.collection("users").document(userId)

        // Load existing data
        userDocRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                etName.setText(doc.getString("name") ?: "")
                etAge.setText(doc.getLong("age")?.toString() ?: "")
            }
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val ageText = etAge.text.toString().trim()

            if (name.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(this, "Nama dan umur harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageText.toIntOrNull() ?: 0
                val userMap = mapOf("name" to name, "age" to age)

                userDocRef.set(userMap).addOnSuccessListener {
                    Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan data.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val iconCatatan = findViewById<ImageView>(R.id.img_book)
        val iconKalender = findViewById<ImageView>(R.id.img_kekalender)
        val iconProfile = findViewById<ImageView>(R.id.img_account)

        iconCatatan.setOnClickListener {
            if (getLocalClassName() != "catatanku") {
                startActivity(Intent(this, catatanku::class.java))
            }
        }

        iconKalender.setOnClickListener {
            if (getLocalClassName() != "Kalender") {
                startActivity(Intent(this, Kalender::class.java))
            }
        }

        iconProfile.setOnClickListener {
            Toast.makeText(this, "Kamu sudah di halaman Profile", Toast.LENGTH_SHORT).show()
        }
    }
}
