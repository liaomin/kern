package com.hitales.utils

import platform.posix.drand48
import platform.posix.srand48
import platform.posix.time

actual fun random():Double {
    srand48(time(null))
    return drand48()
}
