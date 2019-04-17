package com.hitales.liam.ui


import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

actual class Platform {

    actual companion object {

        actual val windowWidth:Float by lazy { platform!!.windowWidth }
        actual val windowHeight:Float by lazy { platform!!.windowHeight }

        actual val os:String = PLATFORM_ANDROID

        private var platform:Platform? = null

        actual fun getInstance():Platform {
            return platform!!
        }

        fun init(rootActivity: Activity){
            platform = Platform(rootActivity)

            var c =  TestController()
            c.onCreate()
            c.view?.apply {
                rootActivity.setContentView(getWidget())
            }

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
        windowWidth = dm.widthPixels.toFloat()
        windowHeight = dm.heightPixels.toFloat()

    }

//    actual fun push(controller: Controller,animation: Animation?){
//        val v = View(context)
//        v.setBackgroundColor(Color.RED)
//        context.setContentView(controller.view)
//    }


}