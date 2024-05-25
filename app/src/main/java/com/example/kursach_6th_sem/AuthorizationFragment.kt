package com.example.kursach_6th_sem

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kursach_6th_sem.api.Api
import com.example.kursach_6th_sem.api.entities.LoginDto
import com.example.kursach_6th_sem.api.entities.UserData
import com.example.kursach_6th_sem.databinding.FragmentAuthorizationBinding
import com.example.kursach_6th_sem.sharedPreference.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding: FragmentAuthorizationBinding get() = _binding!!
    val projectApi = App.retrofit.create(Api::class.java)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        //findNavController().popBackStack(findNavController().graph.startDestinationId, true)



        val context = requireContext()
        val sharedPreference = SharedPrefs(requireContext())
        val edt_email = binding.edtEmail
        val edt_password = binding.edtPassword


        binding.btnLogin.setOnClickListener {
            val str_email_id = edt_email.text.toString()
            val str_password = edt_password.text.toString()

            if(str_email_id.equals("") || str_password.equals("")){
                Toast.makeText(context,"Please Enter Details.", Toast.LENGTH_SHORT).show()
            }else {
                lifecycleScope.launch(Dispatchers.IO){
                    val logUser = projectApi.login(LoginDto(str_email_id, str_password))
                    val userExist: Boolean = (logUser != null)
                    if (userExist) {
                        withContext(Dispatchers.Main){
                            sharedPreference.saveUser(uuid = logUser!!.uuid!!, name = logUser.name, email = logUser.email, "login_status","1")
                            sharedPreference.sharedUser = logUser
                            System.out.println(sharedPreference.sharedUser)
                            findNavController().popBackStack()
                        }
                    } else {
                        withContext(Dispatchers.Main){
                            Toast.makeText(context,"Enter Valid Email Id & Password.", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }
        }


        binding.btnSignUp.setOnClickListener {
            fun_SignUp_PopupWindow()
        }

        return binding.root
    }



    private fun fun_SignUp_PopupWindow() {

        val layoutInflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popView: View = layoutInflater.inflate(R.layout.sign_up_window, null)
        val popupWindow: PopupWindow
        popupWindow = PopupWindow(popView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT,true)
        popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 0)

        val btn_dia_submit = popView.findViewById(R.id.btn_dia_submit) as Button
        btn_dia_submit.setOnClickListener {
            val str_dia_email_id = popView.findViewById<EditText>(R.id.edt_dia_email_id).text.toString()
            val str_dia_password = popView.findViewById<EditText>(R.id.edt_dia_password).text.toString()
            val str_dia_name = popView.findViewById<EditText>(R.id.edt_dia_name).text.toString()



            if(str_dia_email_id.equals("") || str_dia_password.equals("") || str_dia_name.equals("")){
                Toast.makeText(requireContext(),"Please Enter Details.", Toast.LENGTH_SHORT).show()
            }
            else {

                lifecycleScope.launch(Dispatchers.IO) {
                    val checkEmail = projectApi.checkEmail(str_dia_email_id)
                    if (checkEmail.message.equals("true")){
                        withContext(Dispatchers.Main){
                            Toast.makeText(requireContext(),"User with this email is already exist.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        val newUser = UserData(uuid = null, name = str_dia_name, email = str_dia_email_id, password = str_dia_password)
                        System.out.println(newUser)
                        projectApi.createUser(newUser)
                        Log.w("createUser", "success")
                        withContext(Dispatchers.Main){
                            Toast.makeText(requireContext(),"Your Account Created Successfully.", Toast.LENGTH_SHORT).show()
                            popupWindow.dismiss()
                        }
                    }
                }


            }
        }
    }

}