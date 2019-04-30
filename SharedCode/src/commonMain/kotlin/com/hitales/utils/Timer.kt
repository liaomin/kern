package com.hitales.utils

expect fun runOnUiThread(delay: Long = 0,block:(()->Unit))

expect class Timer {

    companion object {

        fun schedule(block:(()->Unit)):Timer

        fun schedule(delay:Long ,block:(()->Unit)):Timer

        fun schedule(delay:Long,period:Long,block:(()->Unit)):Timer

    }


    fun cancle()

    suspend fun join()

    suspend fun cancelAndJoin()


}