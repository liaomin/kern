package com.hitales.ui


import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.view.ViewGroup
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

        fun init(rootActivity: AndroidActivity,debug:Boolean = false,tag:String = "Kern"):ActivityDelegate{
            platform?.onDestroy()
            platform = Platform(rootActivity)
            Platform.debug = debug
            Platform.platformTag = tag
            return  platform!!
        }


        fun getApplication():Application{
            return platform!!.application
        }

        actual var debug: Boolean = false

        var platformTag:String = "Kern"
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
                rootView.addView(view.mWidget,index,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
            }
        }

    }

    fun removeView(args:Array<out Any?>){
        if(args != null && args.isNotEmpty()){
            val view = args[0]
            if(view != null && view is View){
                rootView.removeView(view.mWidget)
            }
        }
    }

    fun setRootView(args:Array<out Any?>){
        rootView.removeAllViews()
        if(args != null && args.isNotEmpty()){
            val view = args[0]
            if(view is View){
                rootView.addView(view.mWidget,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if(Screen.getInstance().window.onBackPressed()){
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onCreate() {
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_REMOVE_VIEW,this::removeView)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_ADD_VIEW,this::addView)
        NotificationCenter.getInstance().addObserver(Window.NOTIFY_KEY_SET_ROOT_VIEW,this::setRootView)
        disableAPIDialog()
    }

    override fun onResume() {
        Screen.getInstance().window.onResume()
    }

    override fun onPause() {
        Screen.getInstance().window.onPause()
    }

    override fun onDestroy() {
        NotificationCenter.getInstance().removeObserver(Window.NOTIFY_KEY_REMOVE_VIEW,this::removeView)
        NotificationCenter.getInstance().removeObserver(Window.NOTIFY_KEY_ADD_VIEW,this::addView)
        NotificationCenter.getInstance().removeObserver(Window.NOTIFY_KEY_SET_ROOT_VIEW,this::setRootView)
        Screen.getInstance().window.onDestroy()
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