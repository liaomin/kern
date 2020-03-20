package com.hitales.ui


import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.hitales.ui.android.AndroidActivity
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.NotificationCenter
import com.hitales.utils.Stack

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
            platform?.onDestory()
            platform = Platform(rootActivity)
            return  platform!!
        }


        fun getApplication():Application{
            return platform!!.application
        }

        actual fun runWithRootController(controller: Controller) {
            platform?.runWithRootController(controller)
        }

        actual var debug: Boolean = false
    }

    val windowWidth:Float

    val windowHeight:Float

    var application:Application

    var rootActivity:AndroidActivity

    val rootView:FrameLayout

    private val stack = Stack<Controller>()

    private fun cleanStack(){
        rootView.removeAllViews()
        stack.forEach {
            it.onPause()
            it.onDestroy()
        }
        stack.clear()
    }


    private constructor(rootActivity: AndroidActivity){
        rootView = FrameLayout(rootActivity)
        rootView.clipChildren = false
        this.application = rootActivity.application
        this.rootActivity = rootActivity
        val dm = application.resources.displayMetrics
        windowWidth = PixelUtil.toDIPFromPixel( dm.widthPixels.toFloat(),dm)
        windowHeight = PixelUtil.toDIPFromPixel( dm.heightPixels.toFloat(),dm)
        rootActivity.setContentView(rootView)

        NotificationCenter.getInstance().addObserver(Controller.NOTIFY_CONTROLLER_PUSH,this::pushNotify)
        NotificationCenter.getInstance().addObserver(Controller.NOTIFY_CONTROLLER_POP,this::popNotify)
    }

    fun pushNotify(any: Any?){
        if(any != null && any is Controller){
            val top = stack.peek()
            if(top != null){
                any.parent = top
                rootView.removeAllViews()
            }
            rootView.addView(any.view.getWidget(),FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT))
        }
    }

    fun popNotify(any: Any?){
        if(any != null && any is Controller){
            val top = stack.peek()
            if(any === top && stack.size() > 1){
                stack.pop()
                rootView.removeAllViews()
                val  v = stack.peek()?.view
                if(v != null){
                    rootView.addView(v.getWidget(),FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT))
                }
            }
        }
    }


    fun runWithRootController(controller: Controller) {
        cleanStack()
        pushNotify(controller)
    }

    override fun onBackPressed(): Boolean {
        val top = stack.peek()
        if(top != null){
            return top.onBackPressed()
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onCreate() {
        disableAPIDialog()
    }

    override fun onResume() {
        stack.peek()?.onResume()
    }

    override fun onPause() {
        stack.peek()?.onPause()
    }

    override fun onDestory() {
       cleanStack()
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