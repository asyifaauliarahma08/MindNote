package com.example.mindnote

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class Kalender : AppCompatActivity() {

    private lateinit var tableCalendar: TableLayout
    private lateinit var tvMonthYear: TextView

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvMonthYear = findViewById(R.id.tvMonthYear)
        tableCalendar = findViewById(R.id.tableCalendar)

        setupBottomNavigation()
        setupCalendar()
    }

    private fun setupBottomNavigation() {
        val iconCatatan = findViewById<ImageView>(R.id.img_book)
        val iconKalender = findViewById<ImageView>(R.id.img_kekalender)
        val iconProfile = findViewById<ImageView>(R.id.img_account)

        iconCatatan.setOnClickListener {
            if (this::class.java.simpleName != "catatanku") {
                startActivity(Intent(this, catatanku::class.java))
            }
        }

        iconKalender.setOnClickListener {
            Toast.makeText(this, "Kamu sudah di halaman Kalender", Toast.LENGTH_SHORT).show()
        }

        iconProfile.setOnClickListener {
            if (this::class.java.simpleName != "Profile") {
                startActivity(Intent(this, Profile::class.java))
                finish() // tutup halaman ini biar nggak numpuk
            }
        }

    }

    private fun setupCalendar() {
        val sdfMonthYear = SimpleDateFormat("MMMM yyyy", Locale("id", "ID"))
        tvMonthYear.text = sdfMonthYear.format(calendar.time)

        tableCalendar.removeAllViews()

        // Hitung offset hari pertama di bulan ini (Minggu = 1)
        val tempCal = calendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayWeek = tempCal.get(Calendar.DAY_OF_WEEK)

        val maxDay = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)

        var dayCounter = 1
        var reachedEnd = false

        // Kita buat 6 baris (max minggu dalam 1 bulan)
        for (row in 0 until 6) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for (col in 1..7) {
                val cell = TextView(this)
                cell.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                cell.gravity = Gravity.CENTER
                cell.setPadding(8, 20, 8, 20)
                cell.setTextColor(Color.BLACK)
                cell.textSize = 16f

                if (row == 0 && col < firstDayWeek) {
                    // Kosong sebelum tanggal 1
                    cell.text = ""
                } else if (dayCounter > maxDay) {
                    // Lewat tanggal terakhir bulan
                    cell.text = ""
                    reachedEnd = true
                } else {
                    cell.text = dayCounter.toString()

                    // Highlight tanggal hari ini
                    val todayCal = Calendar.getInstance()
                    if (calendar.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                        calendar.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH) &&
                        dayCounter == todayCal.get(Calendar.DAY_OF_MONTH)
                    ) {
                        // Background lingkaran warna khusus
                        cell.setBackgroundResource(R.drawable.bg_circle_highlight)
                        cell.setTextColor(Color.WHITE)
                    } else {
                        cell.background = null
                    }

                    // Klik tanggal
                    val tanggalKlik = dayCounter
                    cell.setOnClickListener {
                        Toast.makeText(this, "Kamu klik tanggal $tanggalKlik", Toast.LENGTH_SHORT).show()
                    }

                    dayCounter++
                }
                tableRow.addView(cell)
            }
            tableCalendar.addView(tableRow)

            if (reachedEnd) break
        }
    }
}
