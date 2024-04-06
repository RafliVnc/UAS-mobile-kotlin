package com.example.projekuas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projekuas.databinding.ActivityTampilanMuridBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TampilanMurid : AppCompatActivity(){
    private lateinit var muridList: MutableList<Murid>
    private lateinit var ref: DatabaseReference
    private lateinit var binding: ActivityTampilanMuridBinding

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampilanMuridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id_kelas = intent.getStringExtra(EXTRA_ID)

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
                        this@TampilanMurid,
                        R.layout.activity_item_murid, muridList
                    )
                    binding.lvTambahMurid.adapter = adapter

                    println("Output: " + muridList.sortBy{murid: Murid -> murid.nama.capitalize() })
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.icnAdd.setOnClickListener{ id ->
            val intent1 = Intent(this, TambahMurid::class.java)
            intent1.putExtra(TambahMurid.EXTRA_ID, id_kelas)
            startActivity(intent1)
        }
        binding.btnPrediksi.setOnClickListener {
            val intent1 = Intent(this, TampilanPrediksiMurid::class.java)
            intent1.putExtra(TambahMurid.EXTRA_ID, id_kelas)
            startActivity(intent1)
        }
        binding.lvTambahMurid.setOnItemClickListener { parent, view, position, id ->
            val murid = muridList.get(position)
            val intent = Intent(this, DetailMurid::class.java)
            intent.putExtra(DetailMurid.EXTRA_ID_KELAS, id_kelas)
            intent.putExtra(DetailMurid.EXTRA_ID_MURID, murid.id_murid)
            intent.putExtra(DetailMurid.EXTRA_ID_NAMA, murid.nama)
            intent.putExtra(DetailMurid.EXTRA_ID_FEDU, murid.fedu)
            intent.putExtra(DetailMurid.EXTRA_ID_MEDU, murid.medu)
            intent.putExtra(DetailMurid.EXTRA_ID_STUDYTIME, murid.studytime)
            intent.putExtra(DetailMurid.EXTRA_ID_FAILURE, murid.failure)
            intent.putExtra(DetailMurid.EXTRA_ID_ABSENCES, murid.absences)
            intent.putExtra(DetailMurid.EXTRA_ID_G1, murid.g1)
            intent.putExtra(DetailMurid.EXTRA_ID_G2, murid.g2)
            intent.putExtra(DetailMurid.EXTRA_ID_HIGHER, murid.higher)
            startActivity(intent)
        }
        binding.btnAbsen.setOnClickListener {
            val intent = Intent(this, TambahAbsensi::class.java)
            intent.putExtra(TambahMurid.EXTRA_ID, id_kelas)
            startActivity(intent)
        }


    }
}