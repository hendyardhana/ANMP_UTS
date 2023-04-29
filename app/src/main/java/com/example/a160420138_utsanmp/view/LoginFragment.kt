package com.example.a160420138_utsanmp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel:LoginViewModel
    var usernames = ""
    companion object{
        val sharedusername = "sharedusername"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val shared:SharedPreferences = requireActivity().getSharedPreferences(sharedusername, Context.MODE_PRIVATE)
        val username = shared.getString(sharedusername, "")

        view.findViewById<Button>(R.id.btnLoginLogin).setOnClickListener {
            if(username == ""){
                usernames = view.findViewById<EditText>(R.id.editTextUsernameLogin).text.toString()
                viewModel.getData(usernames)
                viewModel.userLD.observe(viewLifecycleOwner, Observer {
                    val shared: SharedPreferences = requireActivity().getSharedPreferences(sharedusername, Context.MODE_PRIVATE)
                    shared.edit {
                        putString(sharedusername, it.username)
                        apply()
                    }
                    Toast.makeText(context, "Login In as ${it.username}! Please click back button!", Toast.LENGTH_SHORT).show()
                })
                view.findViewById<Button>(R.id.btnLoginLogin).isEnabled = false
            }
            else{
                view.findViewById<Button>(R.id.btnLoginLogin).isEnabled = false
                Toast.makeText(context, "Login In as $username! Please click back button!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}