package com.hitales.utils

expect class WeakReference<T:Any>{
    constructor(referred: T)
    fun clear()
    fun get(): T?
    fun use(block:((item:T)->Unit))
}