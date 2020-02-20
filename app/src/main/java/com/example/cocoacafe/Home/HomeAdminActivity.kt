package com.example.cocoacafe.Home

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.cocoacafe.AdminActivity.History.HistoryActivity
import com.example.cocoacafe.AdminActivity.Menu.MenuActivity
import com.example.cocoacafe.LoginandRegister.LoginActivity
import com.example.cocoacafe.R
import kotlinx.android.synthetic.main.lay_home_admin.*

class HomeAdminActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_home_admin)

        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        imgMenu.startAnimation(slideDown)
        imgHistory.startAnimation(slideDown)

        txtHalamanAdmin.setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))

        }

        imgMenu.setOnClickListener {
            startActivity(Intent(applicationContext, MenuActivity::class.java))
        }

        imgHistory.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }


        imgExits.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

    }
}