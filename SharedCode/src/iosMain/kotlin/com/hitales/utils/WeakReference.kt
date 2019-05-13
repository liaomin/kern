package com.hitales.utils

actual class WeakReference<T :Any> {

    private val mWeakReference:kotlin.native.ref.WeakReference<T>

    actual constructor(referred: T) {
        mWeakReference =kotlin.native.ref.WeakReference(referred)
    }

    actual fun clear() {
        mWeakReference.clear()
    }

    actual fun get(): T? {
        return mWeakReference.get()
    }
}