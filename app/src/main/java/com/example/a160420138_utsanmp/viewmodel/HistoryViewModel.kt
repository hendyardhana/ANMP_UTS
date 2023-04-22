package com.example.a160420138_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160420138_utsanmp.model.Booking
import com.example.a160420138_utsanmp.model.Dokter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryViewModel(application: Application):AndroidViewModel(application) {
    val historyLD = MutableLiveData<ArrayList<Booking>>()
    val historyLoadError = MutableLiveData<Boolean>()
    val historyLoading = MutableLiveData<Boolean>()
    private var queue: RequestQueue? = null
    private val tags = "HistoryTag"

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(tags)
    }

    fun getData(namapasien:String){
        historyLoadError.value = false
        historyLoading.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://hendyardhana.000webhostapp.com/anmp/booking.php?namapasien=$namapasien"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Booking>>() {}.type
                val result = Gson().fromJson<ArrayList<Booking>>(it, sType)

                Log.d("History Data", result.toString())
                historyLD.value = result as ArrayList<Booking>
                historyLoading.value = false
            },
            {
                Log.e("History Data", it.toString())
                historyLoading.value = false
                historyLoadError.value = true
            }
        )
        stringRequest.tag = tags
        queue?.add(stringRequest)
    }
}