package com.example.cocoacafe.UserActivity.Menu.MyHistory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.cocoacafe.AdminActivity.Model.Menus
import com.example.cocoacafe.Home.HomeActivity
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.Model.Orders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OrderHisAdapter (val mCtx: Context, val layoutResId: Int, val list: List<Orders>)
    : ArrayAdapter<Orders>(mCtx,layoutResId,list) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View =layoutInflater.inflate(layoutResId, null)

        val txtname = view.findViewById<TextView>(R.id.txt_img_name_user_his_order)
        val textPriceUser = view.findViewById<TextView>(R.id.txt_img_price_user_his_order)
        val viewGambarUser = view.findViewById<ImageView>(R.id.img_data_user_his_order)


        val menuususer = list[position]

        txtname.text = menuususer.name
        textPriceUser.text = "Rp." + menuususer.price
        Glide.with(mCtx).load(menuususer.image).into(viewGambarUser)

        return view


    }




}