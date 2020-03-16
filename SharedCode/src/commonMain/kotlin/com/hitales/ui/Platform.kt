package com.hitales.ui

//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch


const val PLATFORM_ANDROID = "android"
const val PLATFORM_IOS = "ios"

annotation class TargetPlatform(val platfrom:String)

expect class Platform {
    companion object {
//        val mainLoopDispatcher: CoroutineDispatcher
        val windowWidth:Float
        val windowHeight:Float
        fun getInstance() : Platform
        val os:String
        fun runWithRootController(controller: Controller)
//        fun getCurrentController() : Controller
    }


//   open fun push(controller: Controller,animation: Animation? = null)

}

