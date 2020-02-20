package com.example.cocoacafe.AdminActivity.History


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.cocoacafe.R
import com.example.cocoacafe.UserActivity.Menu.Model.OrderHis
import com.example.cocoacafe.UserActivity.Menu.Model.Orders
import com.google.firebase.database.FirebaseDatabase

class HistoryAdapter(val mCtx: Context, val layoutResId: Int, val list: List<OrderHis>)
    : ArrayAdapter<OrderHis>(mCtx,layoutResId,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View =layoutInflater.inflate(layoutResId, null)

        val textUid  = view.findViewById<TextView>(R.id.txt_img_uid_his)
        val textName = view.findViewById<TextView>(R.id.txt_img_name_his)
        val textPrice = view.findViewById<TextView>(R.id.txt_img_price_his)
        val viewGambar = view.findViewById<ImageView>(R.id.img_data_his)
        val btnDeletehis  = view.findViewById<TextView>(R.id.btnDeleteHis)


        val menuus = list[position]

        textUid.text = "Atas Email :" + menuus.resemail
        textName.text = "Nama pesanan : " + menuus.name
        textPrice.text = "Rp." + menuus.price
        Glide.with(mCtx).load(menuus.image).into(viewGambar)

        btnDeletehis.setOnClickListener {
            deleteHis(menuus)
        }

        return view

    }

    private fun deleteHis(menuus: OrderHis) {
        val progressDialog = ProgressDialog(context,R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("OrderAdmin")
        mydatabase.child(menuus.resIdOrder).removeValue()
        Toast.makeText(mCtx, "Deleted!!!",Toast.LENGTH_SHORT).show()
        val habisdelete = Intent(context, HistoryActivity::class.java)
        context.startActivity(habisdelete)

    }


}