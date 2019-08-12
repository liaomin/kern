package com.hitales.ui

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val PLATFORM_ANDROID = "android"
const val PLATFORM_IOS = "ios"

@Target(AnnotationTarget.CLASS,AnnotationTarget.FIELD,AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class TargetPlatform(val platfrom:String)

suspend fun test() = coroutineScope {
    launch {
        delay(1000)
        println("Kotlin Coroutines World!")
    }
    println("Hello")
}


expect class Platform {
    companion object {
        val mainLoopDispatcher: CoroutineDispatcher
        val windowWidth:Float
        val rootView:ViewGroup
        val windowHeight:Float
        fun getInstance() : Platform
        val os:String
//        fun getCurrentController() : Controller
    }


//   open fun push(controller: Controller,animation: Animation? = null)

}

