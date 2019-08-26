package com.hitales.ui


import android.animation.Animator
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hitales.test.TestController
import com.hitales.ui.android.AndroidActivity
import com.hitales.ui.android.AndroidFragment
import com.hitales.ui.android.ControllerManager
import com.hitales.ui.animation.toAnimator
import com.hitales.ui.utils.PixelUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class ControllerFragment(val controller: Controller) : AndroidFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return controller.view.getWidget()
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
//        enterTransition = Fade()
        val enterAnimator = controller.enterAnimation
        val exitAnimation = controller.exitAnimation
        if(enter){
            if(enterAnimator != null){
                return  enterAnimator.toAnimator(controller.view.getWidget())
            }
        }else{
            if(exitAnimation != null){
                return exitAnimation.toAnimator(controller.view.getWidget())
            }
        }
        return super.onCreateAnimator(transit, enter, nextAnim)
    }

}

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

        fun init(rootActivity: AndroidActivity):ActivityDelegate{
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

    var rootActivity:AndroidActivity

    var c:TestController? = null

    val rootView:FrameLayout

    val controllerManager:ControllerManager

    private constructor(rootActivity: AndroidActivity){
        rootView = FrameLayout(rootActivity)
        this.application = rootActivity.application
        this.rootActivity = rootActivity
        val dm = application.resources.displayMetrics
        windowWidth = PixelUtil.toDIPFromPixel( dm.widthPixels.toFloat(),dm)
        windowHeight = PixelUtil.toDIPFromPixel( dm.heightPixels.toFloat(),dm)
        rootActivity.setContentView(rootView)
        controllerManager = ControllerManager(rootView)
    }

    override fun onBackPressed(): Boolean {
//        return c!!.onBackPressed()
        return controllerManager.pop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun onCreate() {
        disableAPIDialog()
        var testController = TestController()
        c =  testController
        c?.onCreate()
        c?.onResume()
//        c?.view?.apply {
//            rootView.addSubView(this)
//        }
        c?.onControllerChangedListener = {rootController:Controller,pushController:Controller?,removeController:Controller? ->
            if(pushController != null){
                controllerManager.push(pushController)
            }else if(removeController != null){
                controllerManager.pop()
            }
        }

        controllerManager.push(testController)

//        var fragment = ControllerFragment(testController)
//        testController.tag = fragment
//        rootActivity.addFragment(fragment)
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDestory() {
        rootView.removeAllViews()
        rootActivity.setContentView(View(rootActivity))
    }

    private fun disableAPIDialog() {
        if (Build.VERSION.SDK_INT < 28) return
        try {
            val clazz = Class.forName("android.app.ActivityThread")
            val currentActivityThread = clazz.getDeclaredMethod("currentActivityThread")
            currentActivityThread.isAccessible = true
            val activityThread = currentActivityThread.invoke(null)
            val mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}