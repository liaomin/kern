package com.hitales.ui

import com.hitales.utils.Stack


open class Controller {

    var title:String? = null

    var onViewChangedListener:((rootController:Controller,controller:Controller,view:View?)->Unit)? = null

    var view:View? = null

    private var rootController:Controller? = null

    protected var stack:Stack<Controller>? = null

    constructor(){
        rootController = this
        this.onCreate()
    }


    open fun onCreate(){

    }

    open fun onPause() {

    }

    open fun onResume() {

    }

    open fun onDestroy() {

    }

    open fun onLowMemory() {

    }

    open fun onBackPressed():Boolean {
        return this.pop()
    }

    open fun push(controller: Controller,animation: Animation? = null,completion:(()->Unit)? = null){
        if(rootController == null){
            rootController = this
        }
        rootController?.pushStack(controller)
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
        onPushController(controller)
    }

    private fun onPushController(controller: Controller){
        onViewChangedListener?.invoke(this,controller,controller.view)
        controller.onResume()
    }

    private fun onPopController(controller: Controller){
        controller.onPause()
        controller.onDestroy()
        val stack = stack!!
        if(stack.isEmpty()){
            onResume()
            onViewChangedListener?.invoke(this,this,view)
        }else{
            val last = stack.last() as Controller
            last.onResume()
            onViewChangedListener?.invoke(this,last,last.view)
        }
    }

}
