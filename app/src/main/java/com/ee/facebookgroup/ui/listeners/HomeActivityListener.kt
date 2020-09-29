package com.ee.facebookgroup.ui.listeners

import android.net.Uri
import android.widget.ViewFlipper

interface HomeActivityListener {
    fun showProgressBar()
    fun hideProgressBar()
    fun setViewFlipperImages(uriList: ArrayList<Uri>?, imgFlipper: ViewFlipper)
}