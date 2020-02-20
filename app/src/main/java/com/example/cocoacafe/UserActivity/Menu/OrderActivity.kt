package com.example.cocoacafe.UserActivity.Menu

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.cocoacafe.AdminActivity.Model.Menus
import com.example.cocoacafe.Home.HomeActivity
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.Model.Orders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_order.*
import kotlinx.android.synthetic.main.content_user_order.*
import org.jetbrains.anko.toast

class OrderActivity: AppCompatActivity() {

    val fbAuth = FirebaseAuth.getInstance()

    lateinit var list : MutableList<Orders>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order)

        val akunPesan = fbAuth.currentUser!!.uid

        val ref = FirebaseDatabase.getInstance().getReference("/Order").orderByChild("uid").equalTo(akunPesan)

        fab_order_home_user.setOnClickListener {
            startActivity(Intent(applicationContext,HomeActivity::class.java))
        }

        imgBackMenu.setOnClickListener {
            startActivity(Intent(applicationContext,MenuUserActivity::class.java))
        }



        list = mutableListOf()
        listView = findViewById(R.id.lv_user_order)



        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {

                var count = 0

                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(Orders::class.java)
                        list.add(user!!)
                        //for total in listview
                        val price = h.getValue(Orders::class.java)?.price
                        val pValue  = price?.toInt()
                        count += pValue!!


                    }

                    btn_checkout.setOnClickListener {
                        p0.ref.removeValue()
                        toast("silahkan menunggu pesanan anda")
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                    }


                    txt_hasil_total.text = count.toString() //put in txt

                    val adapter = OrderAdapter(this@OrderActivity,R.layout.items_order,list)
                    listView.adapter = adapter


                }
            }
        })


    }


}