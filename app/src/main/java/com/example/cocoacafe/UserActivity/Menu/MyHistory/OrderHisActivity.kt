package com.example.cocoacafe.UserActivity.Menu.MyHistory

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
import kotlinx.android.synthetic.main.activity_his_order.*
import kotlinx.android.synthetic.main.content_user_history_order.*
import kotlinx.android.synthetic.main.content_user_order.*
import org.jetbrains.anko.toast

class OrderHisActivity: AppCompatActivity() {

    val fbAuth = FirebaseAuth.getInstance()

    lateinit var list : MutableList<Orders>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_his_order)

        val akunPesan = fbAuth.currentUser!!.uid

        val ref = FirebaseDatabase.getInstance().getReference("/OrderHistory").orderByChild("uid").equalTo(akunPesan)


        list = mutableListOf()
        listView = findViewById(R.id.lv_user_his_order)

        fab_hisHome.setOnClickListener {
            startActivity(Intent(applicationContext,HomeActivity::class.java))
        }


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

                    txt_hasil_total_his_order.text = count.toString() //put in txt

                    val adapter = OrderHisAdapter(this@OrderHisActivity,R.layout.items_history_order,list)
                    listView.adapter = adapter


                }
            }
        })


    }


}