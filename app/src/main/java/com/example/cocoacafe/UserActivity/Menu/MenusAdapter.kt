package com.example.cocoacafe.UserActivity.Menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.Model.OrderHis
import com.example.cocoacafe.UserActivity.Menu.Model.Orders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MenusAdapter (val mCtx: Context, val layoutResId: Int, val list: List<Orders>)
    : ArrayAdapter<Orders>(mCtx,layoutResId,list){

    val fbAuth = FirebaseAuth.getInstance()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View =layoutInflater.inflate(layoutResId, null)

        val txtname = view.findViewById<TextView>(R.id.txt_img_name_user)
        val textPriceUser = view.findViewById<TextView>(R.id.txt_img_price_user)
        val viewGambarUser = view.findViewById<ImageView>(R.id.img_data_user)
        val btnOrder = view.findViewById<Button>(R.id.btnOrder)

        val ordersm = list[position]


        txtname.text = ordersm.name
        textPriceUser.text = "Rp." + ordersm.price
        Glide.with(mCtx).load(ordersm.image).into(viewGambarUser)


        btnOrder.setOnClickListener {
            orderUser(ordersm)
        }

        return view


    }

    private fun orderUser(menuususer: Orders) {



        val nameOrder = menuususer.name
        val priceOrder  = menuususer.price
        val imageOrder = menuususer.image



        val mDatabase = FirebaseDatabase.getInstance().getReference("Order")
        val mDatabaseHisOrder = FirebaseDatabase.getInstance().getReference("OrderHistory")
        val mDatabaseAdmin = FirebaseDatabase.getInstance().getReference("OrderAdmin")

        val authUser = fbAuth.currentUser!!.uid
        val authUserEmail = fbAuth.currentUser!!.email.toString()


        val resid = mDatabase.push().key.toString()
        val residHistory = mDatabaseHisOrder.push().key.toString()
        val residAdmin = mDatabaseAdmin.push().key.toString()

        val resecerv = Orders(authUser,resid,nameOrder,priceOrder,imageOrder)
        val resecervHistory = Orders(authUser,residHistory,nameOrder,priceOrder,imageOrder)
        val resecervAdmin = OrderHis(residAdmin,authUserEmail,authUser,residAdmin,nameOrder,priceOrder,imageOrder)

        mDatabase.child(resid).setValue(resecerv).addOnSuccessListener {
            Toast.makeText(mCtx,"Order Di masukkan", Toast.LENGTH_SHORT).show()
        }

        mDatabaseHisOrder.child(residHistory).setValue(resecervHistory).addOnSuccessListener {  }

        mDatabaseAdmin.child(residAdmin).setValue(resecervAdmin).addOnSuccessListener {

        }

    }




}