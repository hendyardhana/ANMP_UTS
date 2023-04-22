package com.example.a160420138_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160420138_utsanmp.model.Dokter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalTime

class DetailViewModel(application: Application):AndroidViewModel(application) {
    val detailDoctorLD = MutableLiveData<Dokter>()
    private var queue: RequestQueue? = null
    private val tags = "DetailTag"

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(tags)
    }

    fun getData(idDokter:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://hendyardhana.000webhostapp.com/anmp/listdokter.php?idDokter=$idDokter"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<Dokter>() {}.type
                val result = Gson().fromJson<Dokter>(it, sType)

                Log.d("Detail Data", result.toString())
                detailDoctorLD.value = result
            },
            {
                Log.e("Detail Data", it.toString())
            }
        )
        stringRequest.tag = tags
        queue?.add(stringRequest)
    }
}