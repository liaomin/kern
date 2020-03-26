package com.hitales.ui


import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.hitales.ui.android.AndroidActivity
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.NotificationCenter

//class ControllerFragment(val controller: Controller) : AndroidFragment(){
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return controller.view.getWidget()
//    }
//
//    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
////        enterTransition = Fade()
//        val enterAnimator = controller.enterAnimation
//        val exitAnimation = controller.exitAnimation
//        if(enter){
//            if(enterAnimator != null){
//                return  enterAnimator.toAnimator(controller.view.getWidget())
//            }
//        }else{
//            if(exitAnimation != null){
//                return exitAnimation.toAnimator(controller.view.getWidget())
//            }
//        }
//        return super.onCreateAnimator(transit, enter, nextAnim)
//    }
//
//}

actual class Platform : ActivityDelegate{

    actual companion object {

//        actual val mainLoopDispatcher: CoroutineDispatcher = Dispatchers.Main

        actual val windowWidth:Float by lazy { platform!!.windowWidth }
        actual val windowHeight:Float by lazy { platform!!.windowHeight }


        val displayMetrics : DisplayMetrics by lazy { platform!!.application.resources.displayMetrics }

        actual val os:String = PLATFORM_ANDROID

        private var platform:Platform? = null

        actual fun getInstance():Platform {
            return platform!!
        }

        fun init(rootActivity: AndroidActivity):ActivityDelegate{
            platform?.onDestroy()
            platform = Platform(rootActivity)
            return  platform!!
        }


        fun getApplication():Application{
            return platform!!.application
        }

        actual var debug: Boolean = false
    }

    val windowWidth:Float

    val windowHeight:Float

    var application:Application

    var rootActivity:AndroidActivity

    val rootView:FrameLayout

    private constructor(rootActivity: AndroidActivity){
        rootView = FrameLayout(rootActivity)
        rootView.clipChildren = false
        this.application = rootActivity.application
        this.rootActivity = rootActivity
        val dm = application.resources.displayMetrics
        windowWidth = PixelUtil.toDIPFromPixel( dm.widthPixels.toFloat(),dm)
        windowHeight = PixelUtil.toDIPFromPixel( dm.heightPixels.toFloat(),dm)
        Screen.init(windowWidth,windowHeight)
        rootActivity.setContentView(rootView)
    }

    fun addView(view:Any? = null){
        if(view != null && view is View){
            rootView.addView(view.mWidget)
        }
    }

    fun removeView(view:Any? = null){
        if(view != null && view is View){
            rootView.removeView(view.mWidget)
        }
    }

    fun removeAllAndAddView(view:Any? = null){
        rootView.removeAllViews()
        if(view != null && view is View){
            val v = view.mWidget
            rootView.addView(view.mWidget)
        }
    }

    override fun onBackPressed(): Boolean {
        if(Screen.getInstsance().window.onBackPressed()){
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onCreate() {
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_REMOVE_VIEW,this::removeView)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_ADD_VIEW,this::addView)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_REMOVE_ALL_AND_ADD_VIEW,this::removeAllAndAddView)
        disableAPIDialog()
    }

    override fun onResume() {
        Screen.getInstsance().window.onResume()
    }

    override fun onPause() {
        Screen.getInstsance().window.onPause()
    }

    override fun onDestroy() {
        NotificationCenter.getInstance().removeObserver(Window.NOTIFY_KEY_REMOVE_VIEW,this::removeView)
        NotificationCenter.getInstance().removeObserver(Window.NOTIFY_KEY_ADD_VIEW,this::addView)
        NotificationCenter.getInstance().removeObserver(Window.NOTIFY_KEY_REMOVE_ALL_AND_ADD_VIEW,this::removeAllAndAddView)
        Screen.getInstsance().window.onDestroy()
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