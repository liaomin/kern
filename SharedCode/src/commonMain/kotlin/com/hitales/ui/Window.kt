package com.hitales.ui

import com.hitales.utils.NotificationCenter
import com.hitales.utils.Stack
import com.hitales.utils.WeakReference

class Window {

    companion object{
        const val NOTIFY_KEY_SET_ROOT_VIEW = "__NOTIFY_KEY_SET_ROOT_VIEW__"
        const val NOTIFY_KEY_ADD_VIEW = "__NOTIFY_KEY_ADD_VIEW__"
        const val NOTIFY_KEY_REMOVE_VIEW = "__NOTIFY_KEY_REMOVE_VIEW__"
    }

    var width:Float

    var height:Float

    var defaultEnterAnimation:Animation? = null

    var defaultExitAnimation:Animation? = null

    private val viewControllerStack = Stack<ViewController>()

    internal constructor(width:Float,height:Float){
        this.width = width
        this.height = height
    }

    fun onFrameChange(width:Float,height:Float){
        this.width = width
        this.height = height
    }

    var rootViewController:ViewController? = null
        set(value) {
            field = value
            cleanStack()
            if(value != null){
                value.window = WeakReference(this)
                value.create()
                value.resume()
                viewControllerStack.append(value)
            }
            NotificationCenter.getInstance().notify(NOTIFY_KEY_SET_ROOT_VIEW,rootViewController?.view)
        }


    fun pushViewController(viewController: ViewController,animation: Animation? = null,completion:(()->Unit)? = null){
        val ani = animation ?: viewController.enterAnimation ?: defaultEnterAnimation
        val last = viewControllerStack.last()
        viewController.window = WeakReference(this)
        viewControllerStack.append(viewController)
        viewController.create()
        viewController.resume()
        NotificationCenter.getInstance().notify(NOTIFY_KEY_ADD_VIEW,viewController.view)
        if(ani != null && viewController.view != null){
            viewController.view!!.startAnimation(ani){
                if(last != null){
                    last.pause()
                    NotificationCenter.getInstance().notify(NOTIFY_KEY_REMOVE_VIEW,last.view)
                }
                completion?.invoke()
            }
        }else{
            if(last != null){
                last.pause()
                NotificationCenter.getInstance().notify(NOTIFY_KEY_REMOVE_VIEW,last.view)
            }
        }
    }

    fun popViewController(animation: Animation? = null){
        val stackSize = viewControllerStack.size()
        if(stackSize > 1){
            var pop = viewControllerStack.pop()!!
            var last = viewControllerStack.last()!!
            NotificationCenter.getInstance().notify(NOTIFY_KEY_ADD_VIEW,last.view,0)
            val ani = animation ?: last.exitAnimation ?: defaultExitAnimation
            if(ani != null && pop.view != null){
                pop.view!!.startAnimation(ani){
                    last.resume()
                    NotificationCenter.getInstance().notify(NOTIFY_KEY_REMOVE_VIEW,pop.view)
                    pop.pause()
                    pop.destroy()
                }
            }else{
                last.resume()
                NotificationCenter.getInstance().notify(NOTIFY_KEY_REMOVE_VIEW,pop.view)
                pop.pause()
                pop.destroy()
            }

        }
    }

    open fun onBackPressed():Boolean {
        val stackSize = viewControllerStack.size()
        if(stackSize > 1){
            val last = viewControllerStack.last()!!
            if (!last.onBackPressed()){
                popViewController()
                return true
            }
        }
        return false
    }

    fun onResume() {
        viewControllerStack.last()?.resume()
    }

    fun onPause() {
        viewControllerStack.last()?.pause()
    }

    fun onDestroy() {
        cleanStack()
    }

    fun onLowMemory() {
        viewControllerStack.forEach {
            it.lowMemory()
        }
    }

    private fun cleanStack(){
        viewControllerStack.forEach {
            it.pause()
            it.destroy()
        }
        viewControllerStack.clear()
    }
}