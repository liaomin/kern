package com.hitales.ui

import com.hitales.utils.NotificationCenter
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.*


actual class Platform {

    @ThreadLocal
    actual companion object {

        actual val windowWidth:Float by lazy { platform!!.windowWidth  }
        actual val windowHeight:Float by lazy { platform!!.windowHeight  }

        actual val os:String = PLATFORM_IOS

        private var platform:Platform? = null

        actual fun getInstance():Platform {
            return platform!!
        }

        fun init(viewController: UIViewController,debug:Boolean = false){
            Platform.debug = debug
            UIScreen.mainScreen.bounds.useContents {
                platform = Platform(this.size.width.toFloat(),this.size.height.toFloat(),viewController)
            }
        }

        actual var debug: Boolean = false

    }

    val windowWidth:Float
    val windowHeight:Float

    val rootViewController:UIViewController


    private constructor(screenWidth:Float,screenHeight: Float,viewController: UIViewController){
        windowWidth = screenWidth
        windowHeight = screenHeight
        rootViewController = viewController
        Screen.init(windowWidth,windowHeight)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_REMOVE_VIEW,this::removeView)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_ADD_VIEW,this::addView)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_SET_ROOT_VIEW,this::setRootView)
    }

    fun addView(args:Array<out Any?>){
        if(args != null && args.isNotEmpty()){
            val view = args[0]
            var index = -1
            if(args.size > 1){
                val i = args[1]
                if(i is Int){
                    index = i
                }
            }
            if(view != null && view is View){
                rootViewController.view.frame.useContents {
                    val widget = view.getWidget()
                    widget.setFrame(CGRectMake(0.0,0.0,this.size.width,this.size.height))
                    if(index >= 0){
                        rootViewController.view.insertSubview(widget,index.toLong())
                    }else{
                        rootViewController.view.addSubview(widget)
                    }
                }

            }
        }

    }

    fun removeView(args:Array<out Any?>){
        if(args != null && args.isNotEmpty()){
            val view = args[0]
            if(view != null && view is View){
                view.getWidget().removeFromSuperview()
            }
        }
    }

    fun setRootView(args:Array<out Any?>){
        if(args != null && args.isNotEmpty()){
            val view = args[0]
            if(view is View){
                rootViewController.view.frame.useContents {
                    val widget = view.getWidget()
                    widget.setFrame(CGRectMake(0.0,0.0,this.size.width,this.size.height))
                    rootViewController.view.addSubview(widget)
                }
            }
        }
    }
}
