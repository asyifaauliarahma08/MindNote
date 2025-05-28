package com.example.mindnote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class Penulisan : AppCompatActivity() {
    private lateinit var judulEditText: EditText
    private lateinit var isiEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private val db = FirebaseFirestore.getInstance()
    private var docId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penulisan)

        judulEditText = findViewById(R.id.judulEditText)
        isiEditText = findViewById(R.id.isiEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        docId = intent.getStringExtra("DOC_ID")

        // Kalau dari catatan lama (edit)
        if (docId != null) {
            db.collection("catatan").document(docId!!).get().addOnSuccessListener {
                judulEditText.setText(it.getString("judul"))
                isiEditText.setText(it.getString("isi"))
            }
        }

        saveButton.setOnClickListener {
            val data = hashMapOf(
                "judul" to judulEditText.text.toString(),
                "isi" to isiEditText.text.toString(),
                "pinned" to false,
                "tanggal" to FieldValue.serverTimestamp()
            )

            if (docId == null) {
                db.collection("catatan").add(data)
            } else {
                db.collection("catatan").document(docId!!).update(data as Map<String, Any>)
            }

            finish()
        }

        deleteButton.setOnClickListener {
            docId?.let {
                db.collection("catatan").document(it).delete()
            }
            finish()
        }
    }
}
