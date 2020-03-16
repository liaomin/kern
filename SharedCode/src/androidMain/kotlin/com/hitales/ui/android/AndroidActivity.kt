package com.hitales.ui.android

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.hitales.ui.ActivityDelegate
import com.hitales.ui.Platform

open class AndroidActivity : FragmentActivity() {

    var mDelegate: ActivityDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate = Platform.init(this)
        mDelegate?.onCreate()
    }

    override fun onResume() {
        super.onResume()
        mDelegate?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mDelegate?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelegate?.onDestory()
    }

    override fun onBackPressed() {
        if(mDelegate != null && mDelegate!!.onBackPressed()){
            return
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mDelegate?.onActivityResult(requestCode,resultCode,data)
    }


    override fun getResources(): Resources {
        val res = super.getResources()
        val configuration = res.configuration
        if (configuration.fontScale != 1.0f) {//fontScale要缩放的比例
            configuration.fontScale = 1.0f
            res.updateConfiguration(configuration, res.displayMetrics)
        }
        return res
    }

}