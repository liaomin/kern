package com.hitales.liam.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle

actual open class Controller{

    actual var view:View? = null
//        get() = window.decorView as View?
//        set(value){
//            this.setContentView(value)
//        }


    actual open fun onCreate(){

    }

    actual open fun onPause() {

    }

    actual open fun onResume() {

    }

    actual open fun onDestroy() {

    }

    actual open fun onLowMemory() {

    }

    actual open fun onBackPressed() {
    }

    actual open fun presentController(controller: Controller,animation: Animation?,completion:(()->Unit)?){

    }

}


