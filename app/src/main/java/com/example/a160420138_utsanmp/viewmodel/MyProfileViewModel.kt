package com.example.a160420138_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160420138_utsanmp.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyProfileViewModel(application: Application):AndroidViewModel(application) {

    val userMyProfileLD = MutableLiveData<User>()
    private var queue: RequestQueue? = null
    private val tags = "UserTag"

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(tags)
    }

    fun getData(username:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://hendyardhana.000webhostapp.com/anmp/myprofile.php?username=$username"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<User>() {}.type
                val result = Gson().fromJson<User>(it, sType)

                Log.d("MyProfile Data", result.toString())
                userMyProfileLD.value = result
            },
            {
                Log.e("MyProfile Data", it.toString())
            }
        )
        stringRequest.tag = tags
        queue?.add(stringRequest)
    }
}