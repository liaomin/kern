package com.hitales.utils

expect class Log{
    companion object {
        fun i(message:String)
        fun e(message:String)
        fun d(message:String)
    }
}