package com.hitales.ui.android

import android.animation.Animator
import android.view.ViewGroup
import com.hitales.ui.Controller
import com.hitales.ui.animation.toAnimator
import java.util.*

class ControllerManager( val rootView: ViewGroup) {

    private val stack = Stack<Controller>()

    fun cleanStack(){
        rootView.removeAllViews()
        stack.forEach {
            it.onPause()
            it.onDestroy()
        }
        stack.clear()
    }

    fun runWithRootController(controller: Controller){
        cleanStack()
        push(controller)
    }

    fun push(controller: Controller){
        var last:Controller? = null
        if(stack.isNotEmpty()){
            last = stack.last()
        }
        stack.push(controller)
        val enterAnimation = controller.enterAnimation
        if(enterAnimation != null){
            val view = controller.view.getWidget()
            rootView.addView(view)
            val animation = enterAnimation.toAnimator(view)
            animation.addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                    if(last != null){
                        rootView.removeView(last.view.getWidget())
                    }
                }

                override fun onAnimationCancel(animator: Animator?) {

                }

                override fun onAnimationStart(animator: Animator?) {
                }

            })
            animation.start()
        }else{
            rootView.removeAllViews()
            rootView.addView(controller.view.getWidget())
        }
    }

    fun pop():Boolean{
        val canPop = stack.size > 1
        val last = stack.pop()
        if(stack.isNotEmpty()){
            val llast = stack.last()
            rootView.addView(llast.view.getWidget(),0)
        }
        if(last != null){
            val view = last.view.getWidget()
            val exitAnimation = last.exitAnimation
            val enterAnimation = last.enterAnimation
            if(exitAnimation != null ){
                val animation = exitAnimation.toAnimator(view)
                animation.addListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animator: Animator?) {
                        rootView.removeView(last.view.getWidget())
                    }

                    override fun onAnimationCancel(animator: Animator?) {

                    }

                    override fun onAnimationStart(animator: Animator?) {
                    }

                })
                animation.start()
            }else{
                rootView.removeView(last.view.getWidget())
            }
        }
        return canPop
    }


}