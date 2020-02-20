package com.example.cocoacafe.UserActivity.Menu

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocoacafe.AdminActivity.Model.Menus
import com.example.cocoacafe.Home.HomeActivity
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.Model.Orders
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_menu.*
import kotlinx.android.synthetic.main.content_user_menu.*

class MenuUserActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Orders>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        fab_myOrder.setOnClickListener {
            startActivity(Intent(applicationContext, OrderActivity::class.java))
        }

        fab_home_menu_user.setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }

        ref = FirebaseDatabase.getInstance().getReference("menus")
        list = mutableListOf()
        listView = findViewById(R.id.lv_user_menu)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(Orders::class.java)
                        list.add(user!!)
                    }
                    val adapter = MenusAdapter(this@MenuUserActivity,R.layout.item_user_menus,list)
                    listView.adapter = adapter
                }
            }
        })

    }
}