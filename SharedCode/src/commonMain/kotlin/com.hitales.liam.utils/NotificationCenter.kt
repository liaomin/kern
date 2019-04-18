package com.hitales.liam.utils

import kotlin.jvm.Synchronized

class NotificationCenter {

    private constructor()

    companion object {

        private val instance_: NotificationCenter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            NotificationCenter()
        }

        fun getInstance(): NotificationCenter {
            return instance_
        }
    }

    val observers = HashMap<Any,LinkedList<((key:Any,value:Any?)->Unit)>>()

    @Synchronized
    fun addObserver(key:Any,block:((key:Any,value:Any?)->Unit)){
        var list = observers.get(key)
        if(list == null){
            list = LinkedList()
            observers.put(key,list)
        }
        list.append(block)
    }

    @Synchronized
    fun removeObserver(key:Any,block:((key:Any,value:Any?)->Unit)){
        var list = observers.get(key)
        if(list == null){
            list = LinkedList()
            observers.put(key,list)
        }
        list.remove(block)
    }

    @Synchronized
    fun notify(key:Any,value: Any?){
        var list = observers.get(key)
        if(list == null){
            list = LinkedList()
            observers.put(key,list)
        }
        list.forEach {
            it(key,value)
        }
    }

}