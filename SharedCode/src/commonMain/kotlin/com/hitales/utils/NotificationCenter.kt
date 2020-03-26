package com.hitales.utils

import kotlin.jvm.Synchronized

class NotificationCenter {

    private constructor()

    companion object {

        private val instance_ =  NotificationCenter()

        fun getInstance(): NotificationCenter {
            return instance_
        }
    }

    private val observers = HashMap<Any,LinkedList<((value:Any?)->Unit)>>()

    fun addObserver(key:Any,block:((value:Any?)->Unit)){
        getObserversForKey(key).append(block)
    }

    fun removeObserver(key:Any,block:((value:Any?)->Unit)){
        getObserversForKey(key).remove(block)
    }


    fun notify(key:Any,value: Any? = null){
        getObserversForKey(key).forEach {
            it(value)
        }
    }

    @Synchronized
    private fun getObserversForKey(key: Any):LinkedList<((value:Any?)->Unit)>{
        var list = observers[key]
        if(list == null){
            list = LinkedList()
            observers[key] = list
        }
        return list
    }

}