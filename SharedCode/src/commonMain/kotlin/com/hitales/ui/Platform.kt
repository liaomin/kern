package com.hitales.ui

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val PLATFORM_ANDROID = "android"
val PLATFORM_IOS = "ios"

suspend fun test() = coroutineScope {
    launch {
        delay(1000)
        println("Kotlin Coroutines World!")
    }
    println("Hello")
}

expect class Platform {
    companion object {
        val windowWidth:Float
        val windowHeight:Float
        fun getInstance() : Platform
        val os:String
//        fun getCurrentController() : Controller
    }


//   open fun push(controller: Controller,animation: Animation? = null)

}

