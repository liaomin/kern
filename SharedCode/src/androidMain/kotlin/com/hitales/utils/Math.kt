package com.hitales.utils

actual fun random():Double {
    return Math.random()
}

actual fun min(a:Float,b:Float):Float {
    return Math.min(a,b)
}

actual fun max(a:Float,b:Float):Float {
    return Math.max(a,b)
}