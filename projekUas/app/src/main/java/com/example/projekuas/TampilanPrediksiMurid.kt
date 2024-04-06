package com.example.projekuas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projekuas.databinding.ActivityTampilanPrediksiBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Collections

class TampilanPrediksiMurid: AppCompatActivity(){
    private lateinit var muridList: MutableList<Murid>
    private lateinit var ref: DatabaseReference
    private lateinit var binding: ActivityTampilanPrediksiBinding

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampilanPrediksiBinding.inflate(layoutInflater)
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

                    val adapter = PrediksiAdapter(
                        this@TampilanPrediksiMurid,
                        R.layout.activity_item_prediksi, muridList
                    )
                    binding.lvPrediksiMurid.adapter = adapter

                    println("Output: " + muridList.sortByDescending{murid: Murid -> murid.g2.toInt() })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}