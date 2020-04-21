package com.hitales.utils

import com.hitales.ui.Platform

actual class Log{

    actual companion object {
        actual fun i(message: String) {
            android.util.Log.i(Platform.platformTag,message)
        }

        actual fun e(message: String) {
            android.util.Log.e(Platform.platformTag,message)
        }

        fun e(e: Exception) {
            e.printStackTrace()
        }

        actual fun d(message: String) {
            if(Platform.debug){
                android.util.Log.d(Platform.platformTag,message)
            }
        }
    }

}