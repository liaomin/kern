package com.hitales.ui

import com.hitales.utils.NotificationCenter


open class Controller(var parent:Controller? = null,var title:String? = null) {

    companion object{
        val NOTIFY_CONTROLLER_PUSH = "NOTIFY_CONTROLLER_PUSH"
        val NOTIFY_CONTROLLER_POP = "NOTIFY_CONTROLLER_POP"
    }

    var tag:Any? = null

    var view:Layout

    var enterAnimation:Animation? = null

    var exitAnimation:Animation? = null

    init {
        view = createLayout()
    }

    open fun createLayout():Layout{
        return Layout()
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

    open fun push(controller: Controller){
        if(controller !== this){
            controller.parent = this
            NotificationCenter.getInstance().notify(NOTIFY_CONTROLLER_PUSH,controller)
        }else{
            throw RuntimeException("Controller can't push it self")
        }
    }

    open fun pop():Boolean{
        val p = this.parent
        if( p != null ){
            parent = null
            NotificationCenter.getInstance().notify(NOTIFY_CONTROLLER_POP,this)
            return true
        }
        return false
    }

}
