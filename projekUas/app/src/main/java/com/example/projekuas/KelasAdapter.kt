package com.example.projekuas

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class KelasAdapter (
    val kelasContext: Context,
    val layoutResId: Int,
    val kelaslist: List<Kelas>
) : ArrayAdapter<Kelas>(kelasContext, layoutResId, kelaslist) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(kelasContext)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val o_tingkat: TextView = view.findViewById(R.id.ou_tingkat)
        val o_nama_kelas: TextView = view.findViewById(R.id.ou_nama_kelas)
        val imgEdit: ImageView = view.findViewById(R.id.icn_edit)

        val kelas = kelaslist[position]

        imgEdit.setOnClickListener {
            updateKelas(kelas)
        }
        o_nama_kelas.text = kelas.tingkat+ " " + kelas.nama_kelas
        o_tingkat.text = "Kelas "+ kelas.tingkat

        return view
    }
    private fun updateKelas(kelas: Kelas) {
        val builder = AlertDialog.Builder(kelasContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(kelasContext)
        val view = inflater.inflate(R.layout.activity_update, null)

        val edtNamaKelas = view.findViewById<EditText>(R.id.upNamaKelas)
        val edtTingkat = view.findViewById<EditText>(R.id.upTingkat)

        edtNamaKelas.setText(kelas.nama_kelas)
        edtTingkat.setText(kelas.tingkat)


        builder.setView(view)

        builder.setPositiveButton("Ubah") { p0, p1 ->
            val dbKelas = FirebaseDatabase.getInstance().getReference("kelas")
            val nama_kelas = edtNamaKelas.text.toString().trim()
            val tingkat = edtTingkat.text.toString()

            if (nama_kelas.isEmpty() or tingkat.isEmpty()) {
                Toast.makeText(
                    kelasContext, "Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setPositiveButton
            }
            val kelas = Kelas(kelas.id_kelas, nama_kelas, tingkat)

            dbKelas.child(kelas.id_kelas).setValue(kelas)
            Toast.makeText(kelasContext, "Data berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal") { p0, p1 -> }

        builder.setNegativeButton("Hapus") { p0, p1 ->
            val dbAnggota = FirebaseDatabase.getInstance().getReference("kelas")
                .child(kelas.id_kelas)
            val dbDetil = FirebaseDatabase.getInstance().getReference("murid")
                .child(kelas.id_kelas)
            dbAnggota.removeValue()
            dbDetil.removeValue()

            Toast.makeText(kelasContext, "Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }

        val alert = builder.create()
        alert.show()
    }
}