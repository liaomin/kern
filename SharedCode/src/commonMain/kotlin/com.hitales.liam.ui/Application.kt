package com.hitales.liam.ui

import com.hitales.liam.utils.LinkedList

class Application {

    companion object {

        private val instance:Application by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            Application()
        }

        fun getInstance():Application{
            return instance
        }
    }


    var rootController:Controller? = null


}