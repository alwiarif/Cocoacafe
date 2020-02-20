package com.example.cocoacafe.AdminActivity.Menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocoacafe.AdminActivity.Model.Menus
import com.example.cocoacafe.Home.HomeAdminActivity
import com.example.cocoacafe.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_image.*
import kotlinx.android.synthetic.main.content_main_image.*
import org.jetbrains.anko.startActivity

class MenuActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Menus>
    lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_image)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity<AddImage>()
        }

        fab_home.setOnClickListener {
            startActivity(Intent(applicationContext, HomeAdminActivity::class.java))

        }

        ref = FirebaseDatabase.getInstance().getReference("menus")
        list = mutableListOf()
        listView = findViewById(R.id.lv_image)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(Menus::class.java)
                        list.add(user!!)
                    }
                    val adapter = ImageAdapter(this@MenuActivity,R.layout.item_menus,list)
                    listView.adapter = adapter
                }
            }
        })

    }

}