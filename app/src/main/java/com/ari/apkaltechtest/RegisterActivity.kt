package com.ari.apkaltechtest

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ari.apkaltechtest.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(){
     lateinit var binding : ActivityRegisterBinding
     lateinit var  auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
       binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()

            if (email.isEmpty()){
                binding.edtEmailRegister.error = "Email Harus Diisi"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailRegister.error = "Email tidak valid"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.edtPasswordRegister.error = "password Harus Diisi"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6){
                binding.edtPasswordRegister.error = "password tidak valid"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            RegisterFirebase(email,password)
        }
    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent =Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }

    }
}