package com.hitales.ui

import android.content.Intent

interface ActivityDelegate{

    fun onBackPressed():Boolean

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onCreate()

    fun onResume()

    fun onPause()

    fun onDestroy()
}