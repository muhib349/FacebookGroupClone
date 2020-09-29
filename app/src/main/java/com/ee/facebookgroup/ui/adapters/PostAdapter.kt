package com.ee.facebookgroup.ui.adapters

import android.content.Context
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ViewFlipper
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import com.ee.facebookgroup.R
import com.ee.facebookgroup.models.Post
import com.ee.facebookgroup.ui.listeners.HomeActivityListener
import kotlinx.android.synthetic.main.layout_post_item.view.*
import java.util.zip.Inflater

class PostAdapter(
    private val postList: ArrayList<Post>,
    private val listener: HomeActivityListener
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){



    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title = itemView.tv_title
        val tv_description = itemView.tv_description
        val vf_post_images = itemView.vf_post_images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item,parent,false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]

        holder.tv_title.text = post.title
        holder.tv_description.text = post.description
        listener.setViewFlipperImages(post.imageList,holder.vf_post_images)

        //holder.imageSwitcher.setFactory { ImageView(context.applicationContext) }
        //holder.imageSwitcher.setImageURI(post.imageList!![0])
       // holder.imageSwitcher.setImageURI(post.imageList!![0])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}