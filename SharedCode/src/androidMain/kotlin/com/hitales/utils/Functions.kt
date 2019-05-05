package com.hitales.utils

import android.os.Looper

actual fun assertTrue(boolean: Boolean){
    assert(boolean)
}

private val mainLopper = Looper.getMainLooper()

actual fun assertUI(){
    assert(mainLopper == Looper.myLooper())
}

actual fun getCurrnetThreadName():String?{
    return Thread.currentThread().name
}