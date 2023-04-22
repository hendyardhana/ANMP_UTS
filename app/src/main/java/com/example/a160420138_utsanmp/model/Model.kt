package com.example.a160420138_utsanmp.model

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class User(
    val username:String,
    val email:String,
    val photo:String
)

data class Dokter(
    val idDokter:String,
    val name:String,
    val specialist:String,
    val photo:String,
    val open:String,
    val close:String,
    val rating:Double,
    val description:String
)

data class Booking(
    @SerializedName("idBooking")
    val idBook:String,
    val namaPasien:String,
    val namaDokter:String,
    val obatYangDiminum:String,
    val tanggalBooking:String
)