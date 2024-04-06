package com.example.projekuas

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekuas.databinding.ActivityDetailMuridBinding
import com.google.firebase.database.FirebaseDatabase

class DetailMurid : AppCompatActivity(){
    private lateinit var binding: ActivityDetailMuridBinding

    companion object {
        const val EXTRA_ID_KELAS = "extra_id_kelas"
        const val EXTRA_ID_MURID = "extra_id_murid"
        const val EXTRA_ID_NAMA = "extra_id_nama"
        const val EXTRA_ID_MEDU = "extra_id_medu"
        const val EXTRA_ID_FEDU = "extra_id_fedu"
        const val EXTRA_ID_STUDYTIME = "extra_id_studytime"
        const val EXTRA_ID_FAILURE = "extra_id_failure"
        const val EXTRA_ID_ABSENCES = "extra_id_absence"
        const val EXTRA_ID_G1 = "extra_id_g1"
        const val EXTRA_ID_G2 = "extra_id_g2"
        const val EXTRA_ID_HIGHER = "extra_id_higher"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMuridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id_kelas = intent.getStringExtra(EXTRA_ID_KELAS)
        val nama = intent.getStringExtra(EXTRA_ID_NAMA)
        var medu = intent.getStringExtra(EXTRA_ID_MEDU)
        var fedu = intent.getStringExtra(EXTRA_ID_FEDU)
        val studytime = intent.getStringExtra(EXTRA_ID_STUDYTIME)
        val failure = intent.getStringExtra(EXTRA_ID_FAILURE)
        val absences = intent.getStringExtra(EXTRA_ID_ABSENCES)
        val g1 = intent.getStringExtra(EXTRA_ID_G1)
        val g2 = intent.getStringExtra(EXTRA_ID_G2)
        var higher = intent.getStringExtra(EXTRA_ID_HIGHER)
        val id_murid = intent.getStringExtra(EXTRA_ID_MURID)

        if (medu == "1"){
            medu = "SD"
        }else if (medu == "2"){
            medu = "SMP"
        }else if (medu == "3"){
            medu = "SMA"
        }else{
            medu ="Sarjana Dll"
        }

        if (fedu == "1"){
            fedu = "SD"
        }else if (fedu == "2"){
            fedu = "SMP"
        }else if (fedu == "3"){
            fedu = "SMA"
        }else{
            fedu ="Sarjana Dll"
        }

        if (higher == "1"){
            higher = "Ingin Lanjut Kuliah"
        }else{
            higher = "Tidak ingin Lanjut Kuliah"
        }

        binding.dtNama.setText(nama)
        binding.dtMedu.setText(medu)
        binding.dtFedu.setText(fedu)
        binding.dtStudytime.setText(studytime)
        binding.dtFailure.setText(failure)
        binding.dtAbsences.setText(absences)
        binding.dtG1.setText("Ranking Ganjil: "+g1)
        binding.dtG2.setText("Ranking Genap: "+g2)
        binding.dtHigher.setText(higher)

        binding.icnEdit.setOnClickListener {
            updateDialog()
        }
    }
    private fun updateDialog(

    ) {
        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Ubah Data Siswa")
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.activity_update_murid, null)

        val edtNama = view.findViewById<EditText>(R.id.upNama)
        val edtFailure = view.findViewById<EditText>(R.id.upFailure)
        val edtMedu = view.findViewById<EditText>(R.id.upMedu)
        val edtFedu = view.findViewById<EditText>(R.id.upFedu)
        val edtHigher = view.findViewById<EditText>(R.id.upHigher)
        val edtG1 = view.findViewById<EditText>(R.id.upG1)
        val edtG2 = view.findViewById<EditText>(R.id.upG2)
        val edtStudytime = view.findViewById<EditText>(R.id.upStudytime)
        val edtAbsences = view.findViewById<EditText>(R.id.upAbsences)

        val id_kelas = intent.getStringExtra(EXTRA_ID_KELAS)
        val nama = intent.getStringExtra(EXTRA_ID_NAMA)
        var medu = intent.getStringExtra(EXTRA_ID_MEDU)
        var fedu = intent.getStringExtra(EXTRA_ID_FEDU)
        val studytime = intent.getStringExtra(EXTRA_ID_STUDYTIME)
        val failure = intent.getStringExtra(EXTRA_ID_FAILURE)
        val absences = intent.getStringExtra(EXTRA_ID_ABSENCES)
        val g1 = intent.getStringExtra(EXTRA_ID_G1)
        val g2 = intent.getStringExtra(EXTRA_ID_G2)
        var higher = intent.getStringExtra(EXTRA_ID_HIGHER)
        val id_murid = intent.getStringExtra(EXTRA_ID_MURID)

        if (medu == "SD"){
            medu = "1"
        }else if (medu == "SMP"){
            medu = "2"
        }else if (medu == "SMA"){
            medu = "3"
        }else{
            medu ="4"
        }

        if (fedu == "SD"){
            fedu = "1"
        }else if (fedu == "SMP"){
            fedu = "2"
        }else if (fedu == "SMA"){
            fedu = "3"
        }else{
            fedu ="4"
        }

        if (higher == "Ingin lanjut"){
            higher = "1"
        }else{
            higher = "2"
        }
        edtNama.setText(nama)
        edtMedu.setText(medu)
        edtFedu.setText(fedu)
        edtStudytime.setText(studytime)
        edtFailure.setText(failure)
        edtAbsences.setText(absences)
        edtG1.setText(g1)
        edtG2.setText(g2)
        edtHigher.setText(higher)

        builder.setView(view)

        builder.setPositiveButton("Ubah") { p0, p1 ->
            val dbMurid =  FirebaseDatabase.getInstance().getReference("murid").child(id_kelas!!)
            val nama = edtNama.text.toString().trim()
            val failure = edtFailure.text.toString()
            val Medu = edtMedu.text.toString()
            val Fedu = edtFedu.text.toString()
            val studytime = edtStudytime.text.toString()
            val absences = edtAbsences.text.toString()
            val higher = edtHigher.text.toString()
            val G1 = edtG1.text.toString()
            val G2 = edtG2.text.toString()
            val G3 = "0"


            if (nama.isEmpty() or failure.isEmpty() or  Medu.isEmpty() or Fedu.isEmpty() or higher.isEmpty() or G1.isEmpty() or G2.isEmpty()) {
                Toast.makeText(
                    this, "Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setPositiveButton
            }

            val murid = Murid(id_murid!!, nama,Medu,Fedu,studytime,failure, absences ,G1,G2,higher,G3)

            dbMurid.child(murid.id_murid).setValue(murid)
            val intent = Intent(this, TampilanMurid::class.java)
            Toast.makeText(this, "Data berhasil di update", Toast.LENGTH_SHORT)
                .show()

            startActivity(intent)
        }
        builder.setNeutralButton("Batal") { p0, p1 -> }

        builder.setNegativeButton("Hapus") { p0, p1 ->
            val dbMurid = FirebaseDatabase.getInstance().getReference("murid")
                .child(id_kelas!!).child(id_murid!!)
            dbMurid.removeValue()

            val intent = Intent(this, TampilanMurid::class.java)

            Toast.makeText(this, "Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()

            startActivity(intent)
        }
        val alert = builder.create()
        alert.show()



    }
}