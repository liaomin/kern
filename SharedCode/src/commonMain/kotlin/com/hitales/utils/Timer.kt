package com.hitales.utils

import com.hitales.ui.Platform
import kotlinx.coroutines.*


fun runOnUiThread(delay: Long,block:(()->Unit)){
    GlobalScope.launch(Platform.mainLoopDispatcher){
        if(delay > 0){
            kotlinx.coroutines.delay(delay)
        }
        block()
    }
}


class Timer {

    companion object {

        fun schedule(block:(()->Unit)):Timer{
            return Timer(block,-1,-1)
        }

        fun schedule(delay:Long ,block:(()->Unit)):Timer{
            return Timer(block,delay,-1)
        }

         fun schedule(delay:Long,period:Long,block:(()->Unit)):Timer{
            return Timer(block,delay,period)
        }


        fun test(){
            val t = Timer.schedule(1,1000){
                println("11111111")
            }
            Companion.schedule(10000){
                t.cancle()
            }

        }

    }

    val block:(()->Unit)
    val delay:Long
    val period:Long
    var job: Job? = null

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


    fun cancle(){
        job?.cancel()
    }


    suspend fun join(){
        job?.join()
        job = null
    }

    suspend fun cancelAndJoin(){
        job?.cancelAndJoin()
        job = null
    }


}