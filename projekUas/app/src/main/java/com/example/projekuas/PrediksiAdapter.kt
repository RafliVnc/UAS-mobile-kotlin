package com.example.projekuas

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class PrediksiAdapter (
    val muridContext: Context,
    val layoutResId: Int,
    val muridlist: List<Murid>
) : ArrayAdapter<Murid>(muridContext, layoutResId, muridlist) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(muridContext)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val o_nama: TextView = view.findViewById(R.id.ou_nama_siswa)
        val o_prediksi: TextView = view.findViewById(R.id.ou_prediksi)

        val url = "https://raflivnc.pythonanywhere.com/predict"

        val murid = muridlist[position]

        o_nama.text = murid.nama
//        o_prediksi.text = murid.g3

        val jsonObject = JSONObject()
        jsonObject.put("Medu", murid.medu)
        jsonObject.put("Fedu", murid.fedu)
        jsonObject.put("studytime", murid.studytime)
        jsonObject.put("failures", murid.failure)
        jsonObject.put("absences", murid.absences)
        jsonObject.put("G1", murid.g1)
        jsonObject.put("G2", murid.g2)
        jsonObject.put("higherYes",murid.higher)

        val requestBody = jsonObject.toString()
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, jsonObject,
            Response.Listener { response ->
                try {
                    val data = response.getString("G3")
                    o_prediksi.text = data
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                val errorMessage = error?.message ?: "Unknown error"
                Toast.makeText(muridContext, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("NetworkError", "Volley error: $error")
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }
            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charset.defaultCharset())
            }
        }
        val queue = Volley.newRequestQueue(muridContext)
        queue.add(jsonObjectRequest)

        return view
    }

}