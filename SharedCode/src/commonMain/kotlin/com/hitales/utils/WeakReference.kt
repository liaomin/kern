package com.hitales.utils

import kotlin.reflect.KProperty

expect class WeakReference<T:Any>{
    constructor(referred: T)
    fun clear()
    fun get(): T?
    fun use(block:((item:T)->Unit))
}

/**
 * 模拟ios __weak
 */
class WeakDelegate<T : Any> {

    var weakReference:WeakReference<T>? = null

    operator fun getValue(thisRef: Any,d: KProperty<*>):T?{
        return weakReference?.get()
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if(value != null){
            weakReference = WeakReference(value)
        }else{
            weakReference = null
        }
    }
}