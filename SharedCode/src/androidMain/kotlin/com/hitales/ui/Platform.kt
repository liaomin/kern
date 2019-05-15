package com.hitales.ui


import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.hitales.test.TestController
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame
import com.hitales.utils.NotificationCenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext



actual class Platform : ActivityDelegate{


    actual companion object {

        actual val mainLoopDispatcher: CoroutineDispatcher = Dispatchers.Main

        actual val windowWidth:Float by lazy { platform!!.windowWidth }
        actual val windowHeight:Float by lazy { platform!!.windowHeight }

        val displayMetrics : DisplayMetrics by lazy { platform!!.application.resources.displayMetrics }

        actual val os:String = PLATFORM_ANDROID

        private var platform:Platform? = null

        actual fun getInstance():Platform {
            return platform!!
        }

        fun init(rootActivity: Activity):ActivityDelegate{
            platform = Platform(rootActivity)
            return  platform!!
        }


        fun getApplication():Application{
            return platform!!.application
        }
    }

    val windowWidth:Float
    val windowHeight:Float

    var application:Application

    var rootActivity:Activity
    var c:TestController? = null

    private constructor(rootActivity: Activity){
        this.application = rootActivity.application
        this.rootActivity = rootActivity
        val dm = application.resources.displayMetrics
        windowWidth = PixelUtil.toDIPFromPixel( dm.widthPixels.toFloat(),dm)
        windowHeight = PixelUtil.toDIPFromPixel( dm.heightPixels.toFloat(),dm)
    }

    override fun onBackPressed(): Boolean {
        return c!!.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun onCreate() {
        c =  TestController()
        c?.onCreate()
        c?.onResume()
        c?.view?.apply {
            rootActivity.setContentView(getWidget())
        }
        c?.onViewChangedListener = {rootController:Controller,controller:Controller,view: com.hitales.ui.View? ->
            view?.apply {
                rootActivity.setContentView(getWidget())
            }
        }
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDestory() {
    }


}