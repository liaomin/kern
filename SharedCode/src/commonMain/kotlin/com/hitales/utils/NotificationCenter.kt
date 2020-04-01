package com.hitales.utils

import kotlin.jvm.Synchronized
import kotlin.jvm.Volatile

class NotificationCenter {

    private constructor()

    companion object {

        private val mInstance:NotificationCenter by lazy { NotificationCenter() }

        fun getInstance(): NotificationCenter {
            return mInstance
        }
    }

    @Volatile
    private val observers = HashMap<Any,LinkedList<((args:Array<out Any?>)->Unit)>>()

    fun addObserver(key:Any,block:((args:Array<out Any?>)->Unit)){
        val list = getObserversForKey(key)
        list.append(block)
    }

    fun removeObserver(key:Any,block:((args:Array<out Any?>)->Unit)){
        val list = getObserversForKey(key)
        list.remove(block)
    }

    fun notify(key:Any,vararg args:Any?){
        val list = getObserversForKey(key)
        list.forEach {
            it(args)
        }
    }

    @Synchronized
    private fun getObserversForKey(key: Any):LinkedList<((args:Array<out Any?>)->Unit)>{
        var list = observers[key]
        if(list == null){
            list = LinkedList()
            observers[key] = list
        }
        return list
    }

}