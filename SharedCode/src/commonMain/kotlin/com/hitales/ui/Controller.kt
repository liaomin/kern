package com.hitales.ui


expect open class Controller() {

    var view:View?

    open fun onCreate()

    open fun onPause()

    open fun onResume()

    open fun onDestroy()

    open  fun onLowMemory()

    open fun onBackPressed()

    open fun presentController(controller: Controller,animation: Animation? = null,completion:(()->Unit)? = null)
}
