package com.example.cocoacafe.AdminActivity.Menu

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cocoacafe.AdminActivity.Model.Menus
import com.example.cocoacafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_image.*
import kotlinx.android.synthetic.main.content_add_image.*
import org.jetbrains.anko.toast

class AddImage : AppCompatActivity(), View.OnClickListener {

    private var imgPath: Uri? = null
    lateinit var ref : DatabaseReference

    override fun onClick(v: View?) {
        ref = FirebaseDatabase.getInstance().getReference("menus")
        when (v) {
            fab -> {
                val name = edt_img_name.text.toString()
                val price = edt_img_harga.text.toString()

                val imageId = ref.push().key.toString()


                val storageRef = FirebaseStorage
                    .getInstance()
                    .getReference("menus")
                val databaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("menus")


                storageRef.putFile(imgPath!!)
                    .addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener {

//                            databaseRef.child("image").setValue(it.toString())
                            val url = it!!.toString()
                            val isiMenu = Menus(imageId, name, price, url)
                            databaseRef.child(imageId).setValue(isiMenu)





                            toast("Add Menu Successfully")


                            finish()
                        }
                    }
                    .addOnFailureListener {
                        println("Info File : ${it.message}")
                    }


            }

            btn_choose_file -> {
                val iImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(iImg, 0)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)
        setSupportActionBar(toolbar)

        fab.setOnClickListener(this)
        btn_choose_file.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imgPath = data?.data
        }
    }

}