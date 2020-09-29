package com.ee.facebookgroup.ui

import android.content.ContentResolver
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ee.facebookgroup.R
import com.ee.facebookgroup.models.Post
import com.ee.facebookgroup.ui.adapters.PostAdapter
import com.ee.facebookgroup.ui.listeners.HomeActivityListener
import com.ee.facebookgroup.utils.Values
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() , HomeActivityListener{

    var mAdapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(my_toolbar)
        val toolbar = supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)

        fab_add_post.setOnClickListener(View.OnClickListener {
            Intent(this, AddPostActivity::class.java).also {
                startActivity(it)
            }
        })

        val resources: Resources = getResources()

        val image = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(
                R.drawable.mohib
            ) + '/' + resources.getResourceTypeName(R.drawable.mohib) + '/' + resources.getResourceEntryName(
                R.drawable.mohib
            )
        )

        val image_1 = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(
                R.drawable.mohib_1
            ) + '/' + resources.getResourceTypeName(R.drawable.mohib_1) + '/' + resources.getResourceEntryName(
                R.drawable.mohib_1
            )
        )


        val post = Post(arrayListOf(image_1,image),"Mobile Application Developer","I'm Gazi Mohib. Mobile Application developer using java, kotlin!!")
        Values.allPosts.add(post)

        tv_empty_msg.visibility = if (Values.allPosts.size == 0)  View.VISIBLE else View.GONE

        showProgressBar()
        recyclerview_posts.layoutManager = LinearLayoutManager(this)
        mAdapter = PostAdapter(Values.allPosts, this)
        recyclerview_posts.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        mAdapter?.notifyDataSetChanged()
        hideProgressBar()
        tv_empty_msg.visibility = if (Values.allPosts.size == 0)  View.VISIBLE else View.GONE
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun setViewFlipperImages(uriList: ArrayList<Uri>?, imgFlipper: ViewFlipper){

        for (uri in uriList!!){
            val imageView = ImageView(this)
            imageView.setImageURI(uri)

            imgFlipper.addView(imageView)
            imgFlipper.flipInterval = 2000
            if(uriList.size > 1) imgFlipper.isAutoStart = true
            imgFlipper.setInAnimation(this,android.R.anim.fade_in)
            imgFlipper.setOutAnimation(this,android.R.anim.fade_out)

        }
    }
}
