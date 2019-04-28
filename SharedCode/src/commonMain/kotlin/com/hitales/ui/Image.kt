package com.hitales.ui

expect class Image {
    companion object {
        fun named(name: String):Image?
    }
    private constructor()
    fun getWidth():Int
    fun getHeight():Int
    fun toData():ByteArray?
}