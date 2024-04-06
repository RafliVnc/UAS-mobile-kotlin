package com.example.projekuas

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekuas.databinding.ActivityTesBinding

class tes : AppCompatActivity(){
    private lateinit var binding: ActivityTesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtMedu.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.radioButton ->{
                    Toast.makeText(this,"1",Toast.LENGTH_SHORT).show()
                }
                R.id.radioButton2 ->{
                    Toast.makeText(this,"2",Toast.LENGTH_SHORT).show()
                }
                R.id.radioButton5 ->{
                    Toast.makeText(this,"3",Toast.LENGTH_SHORT).show()
                }
                R.id.radioButton6 ->{
                    Toast.makeText(this,"4",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}