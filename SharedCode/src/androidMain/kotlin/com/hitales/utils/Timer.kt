package com.hitales.utils

import com.hitales.ui.PLATFORM_ANDROID
import com.hitales.ui.Platform
import kotlinx.coroutines.*
import kotlinx.coroutines.launch


actual fun runOnUiThread(delay: Long,block:(()->Unit)){
    GlobalScope.launch(Platform.mainLoopDispatcher){
        if(delay > 0){
            delay(delay)
        }
        block()
    }
}


actual class Timer {

    actual companion object {

        actual fun schedule(block:(()->Unit)):Timer{
            return Timer(block,-1,-1)
        }

        actual fun schedule(delay:Long ,block:(()->Unit)):Timer{
            return Timer(block,delay,-1)
        }

        actual  fun schedule(delay:Long,period:Long,block:(()->Unit)):Timer{
            return Timer(block,delay,period)
        }

    }

    val block:(()->Unit)
    val delay:Long
    val period:Long
    var job:Job? = null

    private constructor(block:(()->Unit),delay:Long = -1, period:Long = -1){
        this.block = block
        this.delay = delay
        this.period = period

        this.job = GlobalScope.launch(Platform.mainLoopDispatcher){
            if(delay > 0){
                kotlinx.coroutines.delay(delay)
            }
            block()
            if(period > 0 ){
                while (true){
                    kotlinx.coroutines.delay(period)
                    block()
                }
            }
        }

    }


    actual fun cancle(){
        job?.cancel()
    }


    actual suspend fun join(){
        job?.join()
        job = null
    }

    actual suspend fun cancelAndJoin(){
        job?.cancelAndJoin()
        job = null
    }


}