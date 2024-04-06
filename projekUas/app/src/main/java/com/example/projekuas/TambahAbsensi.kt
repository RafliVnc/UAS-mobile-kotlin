package com.example.projekuas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Switch
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projekuas.databinding.ActivityItemAbsenBinding
import com.example.projekuas.databinding.ActivityTambahAbsenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TambahAbsensi : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityTambahAbsenBinding
    private lateinit var binding2 : ActivityItemAbsenBinding
    private lateinit var muridList: MutableList<Murid>
    private lateinit var ref: DatabaseReference
    var selectedPosition = -1

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("MMMM d, yyyy hh:mm:ss a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahAbsenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding2 = ActivityItemAbsenBinding.inflate(layoutInflater)

        val id_kelas = intent.getStringExtra(TambahMurid.EXTRA_ID)

        muridList = mutableListOf()

        ref = FirebaseDatabase.getInstance().getReference("murid")
            .child(id_kelas!!)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    muridList.clear()

                    for (a in snapshot.children) {
                        val murid = a.getValue(Murid::class.java)
                        if (murid != null) {
                            muridList.add(murid)
                        }
                    }

                    val adapter = MuridAdapter(
                        this@TambahAbsensi,
                        R.layout.activity_item_absen, muridList
                    )
                    binding.lvAbsenMurid.adapter = adapter

                    binding.lvAbsenMurid.setOnItemClickListener { parent, view, position, id ->
                        selectedPosition = position
                        clickButton(view)
                    }


                    println("Output: " + muridList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.textView.setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)
        TimePickerDialog(
            this,
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        displayFormattedDate(calendar.timeInMillis)
    }

    private fun displayFormattedDate(timestamp: Long) {
        binding.textView.text = formatter.format(timestamp)
        Log.i("Formatting", timestamp.toString())
    }

    fun clickButton(view: View) {
        // Mendapatkan objek Murid dari posisi yang diklik
        val selectedItem = binding.lvAbsenMurid.getItemAtPosition(selectedPosition) as? Murid

        // Loop melalui setiap elemen dalam ListView
        for (i in 0 until binding.lvAbsenMurid.childCount) {
            val listItemView = binding.lvAbsenMurid.getChildAt(i)
            val switchAbsensi = listItemView?.findViewById<Switch>(R.id.switchAbsensi)

            // Mengubah warna Switch menjadi merah hanya untuk item yang diklik
            if (i == selectedPosition) {
                switchAbsensi?.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.switch_thumb_color))
                switchAbsensi?.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.switch_thumb_color))
            } else {
                // Jika bukan item yang diklik, kembalikan ke warna semula
                switchAbsensi?.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.switch_thumb_color_checked))
                switchAbsensi?.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.switch_thumb_color_checked))
            }
        }
    }


}