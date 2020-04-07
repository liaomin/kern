package com.hitales.utils

import android.util.Log
import com.hitales.ui.Platform

actual class Log{

    actual companion object {
        actual fun i(message: String) {
            Log.i(Platform.platformTag,message)
        }

        actual fun e(message: String) {
            Log.e(Platform.platformTag,message)
        }

        actual fun d(message: String) {
            Log.d(Platform.platformTag,message)
        }
    }

}