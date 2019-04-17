package com.hitales.liam.ui


val PLATFORM_ANDROID = "android"
val PLATFORM_IOS = "ios"

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

