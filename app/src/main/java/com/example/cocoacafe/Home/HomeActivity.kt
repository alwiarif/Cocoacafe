package com.example.cocoacafe.Home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.cocoacafe.LoginandRegister.LoginActivity
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.MenuUserActivity
import com.example.cocoacafe.UserActivity.Menu.MyHistory.OrderHisActivity
import com.example.cocoacafe.UserActivity.Menu.PageView.PageView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.lay_home.*
import java.util.*

class HomeActivity: AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    //pager//
    lateinit var dotsLayout : LinearLayout
    lateinit var mPager: ViewPager
    var path: IntArray = intArrayOf(R.drawable.coffee, R.drawable.coffee2, R.drawable.coffe3,R.drawable.coffe4)
    lateinit var dots: Array<ImageView>
    lateinit var adapter : PageView

    var currentPage: Int = 0
    lateinit var timer: Timer
    val DELAY_MS : Long = 3000 // Delay mls
    val PERIOD_MS: Long = 5000
    //end pager//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_home)

        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        imgMenuUser.startAnimation(slideDown)
        imgMenuHisUser.startAnimation(slideDown)

        imgMenuUser.setOnClickListener {
            startActivity(Intent(applicationContext, MenuUserActivity::class.java))
        }

        imgMenuHisUser.setOnClickListener {
            startActivity(Intent(applicationContext, OrderHisActivity::class.java))

        }

        imgExitsUser.setOnClickListener {
            signOut()
            Toast.makeText(this, " Anda telah keluar", Toast.LENGTH_SHORT).show()
            val intentLogout = Intent (this, LoginActivity::class.java)
            startActivity(intentLogout)

        }

        fbAuth.addAuthStateListener {
            if (fbAuth.currentUser == null) {
                this.finish()
            }
        }

        // set pager //
        mPager = findViewById(R.id.viewPager) as ViewPager
        adapter = PageView(this,path)
        mPager.adapter = adapter
        dotsLayout = findViewById(R.id.dotsLayouts) as LinearLayout
        createDots(0)
        updatePage()
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                createDots(position)

            }
        })
        // end pager //


    }

    fun updatePage()
    {
        var handler = Handler()
        val Update: Runnable = Runnable {
            if (currentPage == path.size)
            {
                currentPage = 0
            }
            mPager.setCurrentItem(currentPage++,true)
        }
        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                handler.post(Update)
            }
        },DELAY_MS,PERIOD_MS)
    }

    fun createDots(position: Int)
    {
        if (dotsLayout!=null)
        {
            dotsLayout.removeAllViews()
        }
        dots = Array(path.size,{i -> ImageView(this) })

        for (i in 0..path.size - 1)
        {
            dots[i] = ImageView(this)
            if (i == position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots))
            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots))
            }

            var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            params.setMargins(4,0,4,0)
            dotsLayout.addView(dots[i],params)

        }


    }

    fun signOut(){
        fbAuth.signOut()
    }


}