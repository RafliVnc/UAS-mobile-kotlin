package com.example.projekuas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.example.projekuas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ref: DatabaseReference
    private lateinit var kelaslist: MutableList<Kelas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().getReference("kelas")

        kelaslist = mutableListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    kelaslist.clear()
                    for (a in snapshot.children) {
                        val kelas = a.getValue(Kelas::class.java)
                        if (kelas != null) {
                            kelaslist.add(kelas)
                        }
                    }
                    val adapter = KelasAdapter(
                        this@MainActivity,
                        R.layout.activity_item_kelas, kelaslist
                    )
                    binding.lvHasil.adapter = adapter
                    println("Output : " + kelaslist)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.lvHasil.setOnItemClickListener { parent, view, position, id ->
            val kelas = kelaslist.get(position)
            val intent = Intent(this, TampilanMurid::class.java)
            intent.putExtra(TampilanMurid.EXTRA_ID, kelas.id_kelas)
            startActivity(intent)
        }
        binding.icnAdd.setOnClickListener{
            val intent1 = Intent(this, TambahKelas::class.java)
            startActivity(intent1)
        }
    }

}