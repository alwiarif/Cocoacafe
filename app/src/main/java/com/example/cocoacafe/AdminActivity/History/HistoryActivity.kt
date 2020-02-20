package com.example.cocoacafe.AdminActivity.History

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.cocoacafe.Home.HomeAdminActivity
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.Model.OrderHis
import com.example.cocoacafe.UserActivity.Menu.Model.Orders
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_history.*
import kotlinx.android.synthetic.main.activity_main_image.*

class HistoryActivity : AppCompatActivity() {

    lateinit var list : MutableList<OrderHis>
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_history)
        setSupportActionBar(toolbar)

        fab_hisBack.setOnClickListener {
            startActivity(Intent(applicationContext, HomeAdminActivity::class.java))
        }

        val ref = FirebaseDatabase.getInstance().getReference("OrderAdmin")
        list = mutableListOf()
        listView = findViewById(R.id.lv_image_his)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(OrderHis::class.java)
                        list.add(user!!)
                    }
                    val adapter = HistoryAdapter(this@HistoryActivity,R.layout.item_his_menus,list)
                    listView.adapter = adapter
                }
            }
        })

    }

}