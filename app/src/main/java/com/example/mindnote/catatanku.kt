package com.example.mindnote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class catatanku : AppCompatActivity(), CatatanAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addNoteButton: Button
    private lateinit var adapter: CatatanAdapter
    private val catatanList = mutableListOf<Catatan>()

    private val db = FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catatanku)

        recyclerView = findViewById(R.id.recyclerViewCatatan)
        addNoteButton = findViewById(R.id.addNoteButton)

        adapter = CatatanAdapter(catatanList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addNoteButton.setOnClickListener {
            startActivity(Intent(this, Penulisan::class.java))
        }

        setupBottomNavigation()
        loadData()
    }

    private fun setupBottomNavigation() {
        val iconCatatan = findViewById<ImageView>(R.id.img_book)
        val iconKalender = findViewById<ImageView>(R.id.img_kekalender)
        val iconProfile = findViewById<ImageView>(R.id.img_account)

        iconCatatan.setOnClickListener {
            Toast.makeText(this, "Kamu sudah di halaman Catatan", Toast.LENGTH_SHORT).show()
        }

        iconKalender.setOnClickListener {
            if (getLocalClassName() != "Kalender") {
                startActivity(Intent(this, Kalender::class.java))
            }
        }

        iconProfile.setOnClickListener {
            Toast.makeText(this, "Klik Profile", Toast.LENGTH_SHORT).show() // Debug
            startActivity(Intent(this, Profile::class.java))

        }
    }

    private fun loadData() {
        listenerRegistration = db.collection("catatan")
            .orderBy("tanggal")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Toast.makeText(this@catatanku, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val list = mutableListOf<Catatan>()
                for (doc in snapshots!!) {
                    val catatan = Catatan(
                        id = doc.id,
                        judul = doc.getString("judul") ?: "",
                        isi = doc.getString("isi") ?: ""
                    )
                    list.add(catatan)
                }

                adapter.updateData(list)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration?.remove()
    }

    override fun onItemClick(catatan: Catatan) {
        val intent = Intent(this, Penulisan::class.java)
        intent.putExtra("DOC_ID", catatan.id)
        startActivity(intent)
    }

    override fun onItemDelete(catatan: Catatan) {
        db.collection("catatan").document(catatan.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this@catatanku, "Catatan dihapus", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@catatanku, "Gagal menghapus catatan", Toast.LENGTH_SHORT).show()
            }
    }
}
