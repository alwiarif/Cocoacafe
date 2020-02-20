package com.example.cocoacafe.AdminActivity.Menu

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocoacafe.AdminActivity.Model.Menus
import com.example.cocoacafe.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_menus.view.*
import org.jetbrains.anko.toast

class ImageAdapter(val mCtx: Context, val layoutResId: Int, val list: List<Menus>)
    : ArrayAdapter<Menus>(mCtx,layoutResId,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View =layoutInflater.inflate(layoutResId, null)

        val textName = view.findViewById<TextView>(R.id.txt_img_name)
        val textPrice = view.findViewById<TextView>(R.id.txt_img_price)
        val textUpdate = view.findViewById<TextView>(R.id.txtUpdate)
        val textDelete = view.findViewById<TextView>(R.id.txtDelete)
        val viewGambar = view.findViewById<ImageView>(R.id.img_data)

        val menuus = list[position]

        textName.text = menuus.name
        textPrice.text = "Rp." + menuus.price
        Glide.with(mCtx).load(menuus.image).into(viewGambar)


        textUpdate.setOnClickListener {
            showUpdateDialog(menuus)
        }
        textDelete.setOnClickListener {
            deleteInfo(menuus)
        }

        return view




    }

    private fun showUpdateDialog(menuus: Menus) {

        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.lay_update_admin, null)

        val textNamaUpdate = view.findViewById<EditText>(R.id.edt_img_name_update)
        val textPriceUpdate = view.findViewById<EditText>(R.id.edt_img_harga_update)

        //buat ngambil nama dari database ke dalam kolom edittext
        textNamaUpdate.setText(menuus.name)
        textPriceUpdate.setText(menuus.price)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->


            val dbMenusUp = FirebaseDatabase.getInstance().getReference("menus")

            val name = textNamaUpdate.text.toString().trim()
            val price = textPriceUpdate.text.toString().trim()

            if (name.isEmpty()){
                textNamaUpdate.error = "please enter name menu"
                textNamaUpdate.requestFocus()
                return@setPositiveButton
            }

            if (price.isEmpty()){
                textPriceUpdate.error = "please enter price"
                textPriceUpdate.requestFocus()
                return@setPositiveButton
            }
            val updateimage = menuus.image
            val updatemenu = Menus(menuus.id,name,price, updateimage)

            dbMenusUp.child(menuus.id).setValue(updatemenu)

            Toast.makeText(mCtx, "update succcessfully", Toast.LENGTH_SHORT).show()



        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()


    }


    private fun deleteInfo(menuus: Menus) {
        val progressDialog = ProgressDialog(context,R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("menus")
        mydatabase.child(menuus.id).removeValue()
        Toast.makeText(mCtx, "Deleted!!!",Toast.LENGTH_SHORT).show()
        val habisdelete = Intent(context, MenuActivity::class.java)
        context.startActivity(habisdelete)
    }


}