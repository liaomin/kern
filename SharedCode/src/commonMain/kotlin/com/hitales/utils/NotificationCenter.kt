package com.hitales.utils

import kotlin.jvm.Synchronized
import kotlin.native.concurrent.ThreadLocal

class NotificationCenter {

    private constructor()

    companion object {

        private val instance_ =  NotificationCenter()

        fun getInstance(): NotificationCenter {
            return instance_
        }
    }

    private val observers = HashMap<Any,LinkedList<((key:Any,value:Any?)->Unit)>>()

    @Synchronized
    fun addObserver(key:Any,block:((key:Any,value:Any?)->Unit)){
        var list = observers[key]
        if(list == null){
            list = LinkedList()
            observers[key] = list
        }
        list.append(block)
    }

    @Synchronized
    fun removeObserver(key:Any,block:((key:Any,value:Any?)->Unit)){
        var list = observers[key]
        if(list == null){
            list = LinkedList()
            observers[key] = list
        }
        list.remove(block)
    }

    @Synchronized
    fun notify(key:Any,value: Any? = null){
        var list = observers[key]
        if(list == null){
            list = LinkedList()
            observers[key] = list
        }
        list.forEach {
            it(key,value)
        }
    }

}