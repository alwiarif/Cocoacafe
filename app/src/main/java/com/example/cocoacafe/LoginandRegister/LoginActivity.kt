package com.example.cocoacafe.LoginandRegister

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cocoacafe.Home.HomeActivity
import com.example.cocoacafe.Home.HomeAdminActivity
import com.example.cocoacafe.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lay_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_login)

        //////Animasi//////
        val animationBackground = layLogin.background as AnimationDrawable
        animationBackground.setEnterFadeDuration(2500)
        animationBackground.setExitFadeDuration(2500)
        animationBackground.start()

        //////Intent//////
        txtGetAcc.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        //////Click//////
        btn_submit.setOnClickListener {

            val email = edt_email.text.toString()
            val password = edt_pass.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Masukkan Email dan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                Log.e("fungsi login", "Login gagal: ${it}")
            }


            if (email == "admin@mail.com" && password == "admin1234"){
                Toast.makeText(this, "Login Berhasill", Toast.LENGTH_SHORT).show()
                val intent = Intent (this,HomeAdminActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val progressDialog = ProgressDialog(this)
                progressDialog.isIndeterminate = true
                progressDialog.setMessage("Login di proses")
                progressDialog.show()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{


                        if (!it.isSuccessful){ return@addOnCompleteListener
                            val intent = Intent (this, HomeActivity::class.java)
                            startActivity(intent)
                            progressDialog.hide()
                        }
                        else
                            Toast.makeText(this, " Login Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent (this, HomeActivity::class.java)
                        startActivity(intent)
                        progressDialog.hide()
                    }
                    .addOnFailureListener{
                        Log.e("fungsi login", "Login gagal: ${it.message}")
                        Toast.makeText(this, "Email/Password ada yang salah", Toast.LENGTH_SHORT).show()
                        progressDialog.hide()

                    }
            }

        }


    }

}