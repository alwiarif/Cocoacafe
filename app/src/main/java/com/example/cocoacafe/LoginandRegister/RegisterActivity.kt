package com.example.cocoacafe.LoginandRegister

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cocoacafe.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lay_register.*

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_register)

        //////Animasi//////
        val animationBackground = layRegister.background as AnimationDrawable
        animationBackground.setEnterFadeDuration(2500)
        animationBackground.setExitFadeDuration(2500)
        animationBackground.start()

        /////////////


        btnRegis.setOnClickListener {
            val email = edt_regisEmail.text.toString()
            val password = edt_regisPass.text.toString()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, "Masukkan Email dan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Register di proses")
            progressDialog.show()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    //Registration OK
                    val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
                    Toast.makeText(this,"Berhasil mendaftar", Toast.LENGTH_SHORT).show()
                    val intent = Intent (this, LoginActivity::class.java)
                    startActivity(intent)
                    progressDialog.hide()

                } else {
                    Toast.makeText(this,"Gagal mendaftar", Toast.LENGTH_SHORT).show()
                    val intentFailed = Intent (this, RegisterActivity::class.java)
                    startActivity(intentFailed)
                    progressDialog.hide()
                }
            }

        }

        txtGetlogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }


    }

}