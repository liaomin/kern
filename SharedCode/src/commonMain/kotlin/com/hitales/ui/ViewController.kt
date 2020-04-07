package com.hitales.ui

import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.utils.WeakReference


open class ViewController(var title:String? = null) {

    companion object{
        const val FLAG_PAUSE = 1
        const val FLAG_DESTROY = 1 shl 1
        const val FLAG_VIEW_DID_LOAD = 1 shl 2
        const val FLAG_VIEW_TRANSITION_ANIMATION = 1 shl 3
    }

    /**
     * default is FLAG_PAUSE | FLAG_DESTROY
     */
    protected var flag:Int = 0

    var tag:Any? = null

    var view:Layout? = null
        set(value) {
            field = value
            if(value != null){
                if(value.layoutParams == null){
                    value.layoutParams = LayoutParams()
                }
            }
        }

    var isShowTransitionAnimation:Boolean
        get() = flag and FLAG_VIEW_TRANSITION_ANIMATION == FLAG_VIEW_TRANSITION_ANIMATION
        set(value) {
            flag = if(value) flag or FLAG_VIEW_TRANSITION_ANIMATION else flag and FLAG_VIEW_TRANSITION_ANIMATION.inv()
        }

    var enterAnimation:Animation? = null
        set(value) {
            field = value
            if(exitAnimation == null && value != null){
                exitAnimation = value.reverse()
            }
        }

    var exitAnimation:Animation? = null

    var window:WeakReference<Window>? = null

    init {
        flag = FLAG_PAUSE or FLAG_DESTROY
    }

    open fun createLayout():Layout{
        return FlexLayout()
    }

    fun create(){
        if(flag and FLAG_DESTROY == FLAG_DESTROY){
            flag = flag and FLAG_DESTROY.inv()
            view = createLayout()
            onCreate()
        }
    }

    fun viewDidLoad(){
        if(flag and FLAG_VIEW_DID_LOAD != FLAG_VIEW_DID_LOAD){
            flag = flag or FLAG_VIEW_DID_LOAD
            onViewDidLoad()
        }
    }

    fun resume(){
        if(flag and FLAG_PAUSE == FLAG_PAUSE){
            flag = flag and FLAG_PAUSE.inv()
            onResume()
        }
    }

    fun pause(){
        if(flag and FLAG_PAUSE != FLAG_PAUSE){
            flag = flag or FLAG_PAUSE
            onPause()
        }
    }

    fun destroy(){
        if(flag and FLAG_DESTROY != FLAG_DESTROY){
            flag = flag or FLAG_DESTROY
            view = null
            onDestroy()
        }
    }

    fun lowMemory(){
        onLowMemory()
    }

    protected open fun onCreate(){
        println("$this onCreate")
    }

    protected open fun onViewDidLoad(){
        println("$this onViewDidLoad")
    }

    protected open fun onPause() {
        println("$this onPause")
    }

    protected  open fun onResume() {
        println("$this onResume")
    }

    protected open fun onDestroy() {
        window = null
        println("$this onDestroy")
    }

    protected open fun onLowMemory() {

    }

    open fun onBackPressed():Boolean {
        return false
    }

    open fun push(viewController: ViewController,animation: Animation? = null,completion:(()->Unit)? = null){
        window?.get()?.pushViewController(viewController,animation, completion)
    }

    open fun pop(){
        window?.get()?.popViewController(exitAnimation)
    }

}
