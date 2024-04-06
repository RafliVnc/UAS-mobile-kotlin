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
import com.example.projekuas.databinding.ActivityItemMuridBinding.*
import com.google.firebase.database.FirebaseDatabase

class MuridAdapter (
    val muridContext: Context,
    val layoutResId: Int,
    val muridlist: List<Murid>
) : ArrayAdapter<Murid>(muridContext, layoutResId, muridlist) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(muridContext)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val o_nama: TextView = view.findViewById(R.id.ou_nama)

        val murid = muridlist[position]

        o_nama.text = murid.nama.capitalize()

        return view
    }
}