package com.hitales.utils

import platform.posix.arc4random

actual fun random():Double {
    return ((arc4random().toLong()%100)+1) / 100.0
}
