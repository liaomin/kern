package com.hitales.utils

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_get_label
import platform.posix.strcmp

actual fun assertTrue(boolean: Boolean){
    assert(boolean)
}

private val main_queue_label = memScoped {
    return@memScoped dispatch_queue_get_label(dispatch_get_main_queue())?.getPointer(this)?.toKString()
}

actual fun assertUI(){
    memScoped {
        assert(strcmp(dispatch_queue_get_label(null)?.getPointer(this)?.toKString(),main_queue_label) == 0)
    }
}

actual fun getCurrentThreadName():String?{
    return memScoped{
        return@memScoped dispatch_queue_get_label(null)?.getPointer(this)?.toKString()
    }
}