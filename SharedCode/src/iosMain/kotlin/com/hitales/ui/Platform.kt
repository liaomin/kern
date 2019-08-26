package com.hitales.ui

import com.hitales.test.TestController
import com.hitales.ui.ios.ControllerManager
import com.hitales.utils.Frame
import kotlinx.cinterop.useContents
import kotlinx.coroutines.*
import platform.UIKit.UIScreen
import platform.UIKit.UIViewController
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

@InternalCoroutinesApi
class MainLoopDispatcher : CoroutineDispatcher(), Delay {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run()
            } catch (err: Throwable) {
                println(err)
                throw err
            }
        }
    }


    @InternalCoroutinesApi
    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000), dispatch_get_main_queue()) {
            try {
                with(continuation) {
                    resumeUndispatched(Unit)
                }
            } catch (err: Throwable) {
                println(err)
                throw err
            }
        }
    }

    @InternalCoroutinesApi
    override fun invokeOnTimeout(timeMillis: Long, block: Runnable): DisposableHandle {
        val handle = object : DisposableHandle {
            var disposed = false
                private set

            override fun dispose() {
                disposed = true
            }
        }
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000), dispatch_get_main_queue()) {
            try {
                if (!handle.disposed) {
                    block.run()
                }
            } catch (err: Throwable) {
                println(err)
                throw err
            }
        }
        return handle
    }

}




actual class Platform {


    @ThreadLocal
    actual companion object {

        actual val mainLoopDispatcher : CoroutineDispatcher = getMainDispatcher()
        actual val windowWidth:Float by lazy { platform!!.windowWidth  }
        actual val windowHeight:Float by lazy { platform!!.windowHeight  }

        actual val os:String = PLATFORM_IOS

        @ThreadLocal
        private var platform:Platform? = null

        actual fun getInstance():Platform {
            return platform!!
        }



        fun init(viewController: UIViewController){
            UIScreen.mainScreen.bounds.useContents {
                platform = Platform(this.size.width.toFloat(),this.size.height.toFloat())
                val controllerManager = platform!!.controllerManager
                viewController.view = controllerManager.rootView.getWidget()

                var c =  TestController()
                c.onCreate()
                controllerManager.push(c)
                c.onResume()
                c.onControllerChangedListener = {_:Controller,pushController:Controller?,removeController:Controller? ->
                    if(pushController != null){
                        controllerManager.push(pushController)
                    }else if(removeController != null){
                        controllerManager.pop()
                    }
                }
            }
        }

        @UseExperimental(kotlinx.coroutines.InternalCoroutinesApi::class)
        private fun getMainDispatcher():CoroutineDispatcher{
            return MainLoopDispatcher()
        }

    }

    val windowWidth:Float
    val windowHeight:Float

    val controllerManager:ControllerManager

    private constructor(screenWidth:Float,screenHeight: Float){
        windowWidth = screenWidth
        windowHeight = screenHeight
        val viewGroup = ViewGroup(Frame(0f,0f,screenWidth,screenHeight))
        controllerManager = ControllerManager(viewGroup)
    }
}