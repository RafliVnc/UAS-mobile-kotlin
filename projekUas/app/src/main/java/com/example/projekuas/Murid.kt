package com.example.projekuas

class Murid(
    val id_murid: String,
    val nama: String,
    val medu: String,
    val fedu: String,
    val studytime: String,
    val failure: String,
    val absences: String,
    val g1: String,
    val g2: String,
    val higher: String,
    var g3: String,
    val isSelected : Boolean=true,
    )  {
    constructor() : this("", "", "","","","","","","","","")

}