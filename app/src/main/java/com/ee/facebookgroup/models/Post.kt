package com.ee.facebookgroup.models

import android.net.Uri

data class Post(
    val imageList: ArrayList<Uri>?,
    val title: String,
    val description: String
)