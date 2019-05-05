package com.hitales.ui


import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.hitales.test.TestController
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame
import com.hitales.utils.NotificationCenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext



actual class Platform {

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

        fun init(rootActivity: Activity){
            platform = Platform(rootActivity)

            var c =  TestController()
            c.onCreate()
            c.onResume()
            c.view?.apply {
                rootActivity.setContentView(getWidget())
            }

            c.onViewChangedListener = {rootController:Controller,controller:Controller,view: com.hitales.ui.View? ->
                view?.apply {
                    rootActivity.setContentView(getWidget())
                }
            }

//            notificationCenter.addObserver(NOTIFY_VIEW_LAYOUT_CHANGE){ key: Any, value: Any? ->
//                val view:com.hitales.liam.ui.View = value as com.hitales.liam.ui.View
//                view.superView?.getWidget()?.requestLayout()
//            }
//            c.view?.frame = Frame(1f,2f,3f,4f)
        }


        fun getApplication():Application{
            return platform!!.application
        }
    }

    val windowWidth:Float
    val windowHeight:Float

    var application:Application

    var rootActivity:Activity

    private constructor(rootActivity: Activity){
        this.application = rootActivity.application
        this.rootActivity = rootActivity
        val dm = application.resources.displayMetrics
        windowWidth = PixelUtil.toDIPFromPixel( dm.widthPixels.toFloat(),dm)
        windowHeight = PixelUtil.toDIPFromPixel( dm.heightPixels.toFloat(),dm)
    }
}