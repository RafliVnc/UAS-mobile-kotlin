package com.example.projekuas

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projekuas.databinding.ActivityTambahMuridBinding
import com.google.firebase.database.*

class TambahMurid : AppCompatActivity(){
    private lateinit var muridList: MutableList<Murid>
    private lateinit var ref: DatabaseReference
    private lateinit var binding: ActivityTambahMuridBinding
    private lateinit var radioButton: RadioButton

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahMuridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id_kelas = intent.getStringExtra(EXTRA_ID)

        muridList = mutableListOf()

        ref = FirebaseDatabase.getInstance().getReference("murid")
            .child(id_kelas!!)

        binding.btnTambahMurid.setOnClickListener {
            simpanMurid()
            val intent1 = Intent(this, TampilanMurid::class.java)
            startActivity(intent1)
        }
    }

    private fun simpanMurid() {
        val nama = binding.edtNama.text.toString().trim()
        val failure = binding.edtFailure.text.toString().trim()
        val radioMedu = binding.edtMedu.checkedRadioButtonId
        radioButton =findViewById(radioMedu)
        var Medu = radioButton.text.toString().trim()
        val radioFedu = binding.edtFedu.checkedRadioButtonId
        radioButton =findViewById(radioFedu)
        var Fedu = radioButton.text.toString().trim()
        val studytime = binding.edtStudytime.text.toString().trim()
        val absences = binding.edtAbsences.text.toString().trim()
        val G1 = binding.edtG1.text.toString().trim()
        val G2 = binding.edtG2.text.toString().trim()
        val radioHigher = binding.edtHigher.checkedRadioButtonId
        radioButton =findViewById(radioHigher)
        var higher = radioButton.text.toString().trim()
        val G3 = "0"

        if (Medu == "SD"){
            Medu = "1"
        }else if (Medu == "SMP"){
            Medu = "2"
        }else if (Medu == "SMA"){
            Medu = "3"
        }else{
            Medu ="4"
        }

        if (Fedu == "SD"){
            Fedu = "1"
        }else if (Fedu == "SMP"){
            Fedu = "2"
        }else if (Fedu == "SMA"){
            Fedu = "3"
        }else{
            Fedu ="4"
        }

        if (higher == "Ingin lanjut"){
            higher = "1"
        }else{
            higher = "2"
        }


        if (nama.isEmpty() or failure.isEmpty() or  Medu.isEmpty() or Fedu.isEmpty() or absences.isEmpty()or studytime.isEmpty()or higher.isEmpty() or G1.isEmpty() or G2.isEmpty() ) {
            Toast.makeText(
                this, "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
        val muridId = ref.push().key
        val murid = Murid(muridId!!, nama,Medu,Fedu,studytime,failure, absences ,G1,G2,higher,G3)

        ref.child(muridId).setValue(murid).addOnCompleteListener {
            Toast.makeText(
                applicationContext, "Informasi tambahan berhasil ditambahkan",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}