package com.hitales.liam.utils

class NotificationCenter {
    companion object {
        private val instance: NotificationCenter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            NotificationCenter()
        }

        fun getInstance(): NotificationCenter {
            return instance
        }
    }
    private constructor()
}