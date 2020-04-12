package com.hitales.utils

import com.hitales.ui.Platform

actual class Log {

    actual companion object {
        actual fun i(message: String) {
        }

        actual fun e(message: String) {
        }

        actual fun d(message: String) {
            if(Platform.debug){
                println(message)
            }
        }
    }

}