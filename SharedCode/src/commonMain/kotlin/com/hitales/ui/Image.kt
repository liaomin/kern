package com.hitales.ui

expect open class Image {
    companion object {
        fun named(name: String):Image?
    }
    private constructor()
    fun getWidth():Int
    fun getHeight():Int
    fun toData():ByteArray?
    fun release()
}