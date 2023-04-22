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

class HomeViewModel(application: Application):AndroidViewModel(application) {
    val homeDoctorLD = MutableLiveData<ArrayList<Dokter>>()
    val homeLoadError = MutableLiveData<Boolean>()
    val homeLoading = MutableLiveData<Boolean>()
    private var queue:RequestQueue? = null
    val tags = "HomeTag"

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(tags)
    }

    fun getData(){
        homeLoadError.value = false
        homeLoading.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://hendyardhana.000webhostapp.com/anmp/listdokter.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Dokter>>() {}.type
                val result = Gson().fromJson<List<Dokter>>(it, sType)
                homeDoctorLD.value = result as ArrayList<Dokter>
                homeLoading.value = false
                Log.d("Home Data", result.toString())
            },
            {
                homeLoading.value = false
                homeLoadError.value = true
                Log.e("Home Data", it.toString())
            }
        )
        stringRequest.tag = tags
        queue?.add(stringRequest)

    }
}