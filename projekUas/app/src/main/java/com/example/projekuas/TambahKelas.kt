package com.example.projekuas


import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projekuas.databinding.ActivityAddKelasBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahKelas: AppCompatActivity(){
    private lateinit var binding: ActivityAddKelasBinding
    private lateinit var ref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().getReference("kelas")

        binding.btnSimpan.setOnClickListener{
            simpanData()
            val intent1 = Intent(this, MainActivity::class.java)
            startActivity(intent1)
        }
    }

    private fun simpanData() {
        val nama_kelas = binding.tbNamaKelas.text.toString().trim()
        val tingkat = binding.tbTingkatKelas.text.toString()

        if (tingkat.isEmpty() or nama_kelas.isEmpty()) {
            Toast.makeText(
                this, "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
        val id_kelas = ref.push().key
        val kelas = Kelas(id_kelas!!, nama_kelas ,tingkat )
        ref.child(id_kelas).setValue(kelas).addOnCompleteListener {
            Toast.makeText(
                applicationContext, "Data berhasil ditambahkan",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}