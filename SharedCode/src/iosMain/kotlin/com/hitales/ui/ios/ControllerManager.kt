package com.hitales.ui.ios

import com.hitales.ui.Controller
import com.hitales.ui.ViewGroup
import com.hitales.utils.Stack


class ControllerManager( val rootView: ViewGroup) {

    private val stack = Stack<Controller>()

    fun push(controller: Controller){
        var last: Controller? = null
        if(stack.isNotEmpty()){
            last = stack.last()
        }
        stack.push(controller)
        val enterAnimation = controller.enterAnimation
        if(enterAnimation != null){
            val view = controller.view
            rootView.addSubView(view)
            view.startAnimation(enterAnimation){
                if(last != null){
                    rootView.removeSubView(last.view)
                }
            }
        }else{
            rootView.removeAllSubViews()
            rootView.addSubView(controller.view)
        }
    }

    fun pop():Boolean{
        val canPop = stack.size() > 1
        val last = stack.pop()
        if(stack.isNotEmpty()){
            val llast = stack.last()
            rootView.addSubView(llast!!.view,0)
        }
        if(last != null){
            val view = last.view
            val exitAnimation = last.exitAnimation
            val enterAnimation = last.enterAnimation
            if(exitAnimation != null ){
                view.startAnimation(exitAnimation){
                    rootView.removeSubView(last.view)
                }
            }else{
                rootView.removeSubView(last.view)
            }
        }
        return canPop
    }


}