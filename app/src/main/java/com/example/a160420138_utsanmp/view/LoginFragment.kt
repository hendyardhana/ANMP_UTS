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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.model.User
import com.example.a160420138_utsanmp.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel:LoginViewModel
    var usernames = ""
    var passwords = ""
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
            usernames = view.findViewById<EditText>(R.id.editTextUsernameLogin).text.toString()
            passwords = view.findViewById<EditText>(R.id.editTextPaswordLogin).text.toString()
            if(!usernames.isEmpty() && !passwords.isEmpty()){
                if(username == ""){
                    viewModel.getData(usernames,passwords)
                    viewModel.userLD.observe(viewLifecycleOwner, Observer {
                        if(it.username == "fail" && it.password == "fail" && it.email == "fail.ed@email.com"){
                            Toast.makeText(context, "Login Failed! Invalid Username/Password", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            val shared: SharedPreferences = requireActivity().getSharedPreferences(sharedusername, Context.MODE_PRIVATE)
                            shared.edit {
                                putString(sharedusername, it.username)
                                apply()
                            }
                            Toast.makeText(context, "Login Sucessfull as ${it.username}! Please Click Back To Start Application", Toast.LENGTH_SHORT).show()
                            activity?.onBackPressed()
                        }
                    })
                }
                else{
                    Toast.makeText(context, "Login Sucessfull as $username! Please Click Back To Start Application", Toast.LENGTH_SHORT).show()
                    activity?.onBackPressed()
                }
            }
            else{
                Toast.makeText(context, "Fill all the Form first!!", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnRegisterLogin).setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
}