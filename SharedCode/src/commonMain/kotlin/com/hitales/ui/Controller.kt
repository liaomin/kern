package com.hitales.ui

import com.hitales.utils.Stack


open class Controller {

    var title:String? = null

    var onControllerChangedListener:((rootController:Controller,pushController:Controller?,removeController:Controller?)->Unit)? = null

    var view:View? = null

    private var rootController:Controller? = null

    protected var stack:Stack<Controller>? = null

    constructor(){
        rootController = this
    }


    open fun onCreate(){

    }

    open fun onPause() {

    }

    open fun onResume() {

    }

    open fun onDestroy() {
        view?.releaseResource()

    }

    open fun onLowMemory() {

    }

    open fun onBackPressed():Boolean {
        return this.pop()
    }

    open fun push(controller: Controller,animation: Animation? = null,completion:(()->Unit)? = null){
        val root=  rootController
        if(root != null){
            root.pushStack(controller)
        }
        if(animation != null){
            controller.view?.startAnimation(animation,completion)
        }
    }

    open fun pop():Boolean{
        if(rootController != this && rootController != null){
            this.onDestroy()
            return rootController!!.pop()
        }else{
            val temp = stack?.pop()
            if(temp != null){
                onPopController(temp)
            }
            return temp != null
        }
    }

    protected fun pushStack(controller: Controller){
        controller.rootController = rootController
        if(stack == null){
            stack = Stack()
        }
        val stack = stack!!
        val last = stack.last()
        if(last != null){
            last.onPause()
        }else{
            this.onPause()
        }
        stack.append(controller)
        controller.onCreate()
        onPushController(controller)
    }

    private fun onPushController(controller: Controller){
        onControllerChangedListener?.invoke(this,controller,null)
        controller.onResume()
    }

    private fun onPopController(controller: Controller){
        controller.onPause()
        onControllerChangedListener?.invoke(this,null,controller)
        controller.onDestroy()
        val stack = stack!!
        if(stack.isEmpty()){
            onResume()
        }else{
            val last = stack.last() as Controller
            last.onResume()
        }
    }
}
