package com.hitales.ui
import platform.UIKit.UIScreen

import com.hitales.utils.Frame
import com.hitales.utils.NotificationCenter
import kotlinx.cinterop.useContents
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIViewController


actual class Platform {

    @ThreadLocal
    actual companion object {
        actual val windowWidth:Float by lazy { platform!!.windowWidth  }
        actual val windowHeight:Float by lazy { platform!!.windowHeight  }

        actual val os:String = PLATFORM_IOS

        @ThreadLocal
        private var platform:Platform? = null

        actual fun getInstance():Platform {
            return platform!!
        }

        fun init(viewController: UIViewController){
            UIScreen.mainScreen.bounds.useContents {
                platform = Platform(this.size.width.toFloat(),this.size.height.toFloat())
            }
            var c =  TestController()
            c.onCreate()
            c.view?.apply {
                viewController.view = getWidget()
            }
        }

    }

    val windowWidth:Float
    val windowHeight:Float


    private constructor(screenWidth:Float,screenHeight: Float){
        windowWidth = screenWidth
        windowHeight = screenHeight
    }
}