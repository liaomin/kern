package com.hitales.ui.ios

import com.hitales.ios.ui.HView
import com.hitales.ui.View
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.memScoped
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIImage
import platform.UIKit.UIView
import platform.UIKit.UIWindow
import platform.darwin.objc_object
import kotlin.math.max


class IOSView(protected val mView: WeakReference<View>) : HView(CGRectMake(0.0,0.0,0.0,0.0)) {


    init {
        println("view init2")
    }

    @ObjCAction
    fun willMoveToWindow(window: UIWindow?){
    }

    override fun displayLayer(layer: CALayer) {
        println("view display layer")
        mView.get()?.mBackground?.onDraw(layer)
    }


    override fun deinit(){
        println("view deinit")
    }

}