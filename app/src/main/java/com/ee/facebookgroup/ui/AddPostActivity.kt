package com.ee.facebookgroup.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.ee.facebookgroup.R
import com.ee.facebookgroup.models.Post
import com.ee.facebookgroup.utils.Values
import com.ee.facebookgroup.utils.makeToast
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_add_post.my_toolbar


class AddPostActivity : AppCompatActivity() {
    private val PICK_IMAGE_CODE = 1

    private var mImages: ArrayList<Uri>? = null
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        setSupportActionBar(my_toolbar)
        val toolbar = supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)


        imageSwitcher.setFactory { ImageView(applicationContext) }

        ib_img_add.setOnClickListener{ pickImagesIntent() }

        ib_next.setOnClickListener {
            if(position < mImages!!.size - 1){
                position++
                imageSwitcher.setImageURI(mImages!![position])
            }else{
                makeToast("No more images")
            }
        }

        ib_back.setOnClickListener {
            if(position > 0){
                position--
                imageSwitcher.setImageURI(mImages!![position])
            }else{
                makeToast("No more images")
            }
        }

        btn_add_post.setOnClickListener {
            savePostData()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun pickImagesIntent(){
        Intent().also {
            it.type = "image/*"
            it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            it.action = Intent.ACTION_OPEN_DOCUMENT
            it.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(it,"Select images"),PICK_IMAGE_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if (requestCode == PICK_IMAGE_CODE){
                mImages = ArrayList()
                if(data!!.clipData != null){
                    val count = data.clipData!!.itemCount
                    for (i in 0 until  count) {
                        val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                        println(imageUri.toString())
                        mImages?.add(imageUri)
                        imageSwitcher.setImageURI(mImages!![0])
                        position = 0
                    }
                }
                else{
                    val imageUri: Uri? = data.data
                    mImages?.add(imageUri!!)
                    imageSwitcher.setImageURI(imageUri)
                    position = 0
                }
            }
        }
    }

    private fun savePostData(){
        val title = et_title.text
        val description = et_description.text

        if(mImages.isNullOrEmpty()){
            makeToast("Please select images")
            return
        }
        else if(title.isNullOrEmpty() || description.isNullOrEmpty() ){
            makeToast("Please enter valid input")
            return
        }
        else if(title.length < 4) {
            et_title.error = "Title length must be greater than 3"
            return
        }
        else if(description.length < 5) {
            et_title.error = "Title length must be greater than 4"
            return
        }
        else{
            val post = Post(mImages, title.toString(), description.toString() )
            Values.allPosts.add(post)
        }
        finish()
    }
}
