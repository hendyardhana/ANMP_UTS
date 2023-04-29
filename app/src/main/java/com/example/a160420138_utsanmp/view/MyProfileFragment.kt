package com.example.a160420138_utsanmp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.model.User
import com.example.a160420138_utsanmp.viewmodel.MyProfileViewModel
import com.squareup.picasso.Picasso

class MyProfileFragment : Fragment() {

    private lateinit var viewModel:MyProfileViewModel

    companion object{
        val sharedusername = "sharedusername"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyProfileViewModel::class.java)

        val shared: SharedPreferences = requireActivity().getSharedPreferences(sharedusername, Context.MODE_PRIVATE)
        val username = shared.getString(sharedusername, "")

        if(username == ""){
            val action = MyProfileFragmentDirections.actionItemMyProfileToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }
        else{
            viewModel.getData(username!!)
            viewModel.userMyProfileLD.observe(viewLifecycleOwner, Observer {
                view.findViewById<EditText>(R.id.inputNameMyProfile).setText(it.username)
                view.findViewById<EditText>(R.id.inputEmailMyProfile).setText(it.email)
                Picasso.get().load(it.photo).resize(150,150).into(view.findViewById<ImageView>(R.id.imageViewMyProfile))
                view.findViewById<EditText>(R.id.inputNameMyProfile).doOnTextChanged { text, start, before, count ->
                    if(text != it.username){
                        view.findViewById<Button>(R.id.btnEditProfile).isEnabled = false
                    }
                    else{
                        view.findViewById<Button>(R.id.btnEditProfile).isEnabled = true
                    }
                }
                view.findViewById<EditText>(R.id.inputEmailMyProfile).doOnTextChanged { text, start, before, count ->
                    if(text != it.email){
                        view.findViewById<Button>(R.id.btnEditProfile).isEnabled = false
                    }
                    else{
                        view.findViewById<Button>(R.id.btnEditProfile).isEnabled = true
                    }
                }
            })
            view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
                shared.edit().clear().apply()
                val action = MyProfileFragmentDirections.actionItemMyProfileToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

}