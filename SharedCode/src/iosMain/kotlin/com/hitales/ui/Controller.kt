package com.hitales.ui


actual open class Controller {

    actual var view:View? = null
        set(value) {
            val temp = field
            field = value
            val superView = temp?.superView
            if(superView != null && value != null){
                superView.addView(value)
            }
        }


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


