package com.hitales.ui

import kotlinx.cinterop.*
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memccpy


actual class Image {

    var mImage:UIImage? = null

    constructor(image: UIImage){
        this.mImage = image
    }
    actual private constructor()

    actual companion object {
        actual fun named(name: String): Image? {
            try {
                return Image(UIImage.imageNamed(name)!!)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return null
        }
    }

//
//    actual fun getWidth(): Int {
//       if(bitmap != null){
//           return  bitmap!!.width
//       }
//       return 0
//    }
//
//    actual fun getHeight(): Int {
//        if(bitmap != null){
//            return  bitmap!!.height
//        }
//        return 0
//    }
//
//    actual fun toData(): ByteArray? {
//        if(bitmap != null){
//            val _bitmap = bitmap!!
//            val bos =  ByteArrayOutputStream()
//            _bitmap.compress(Bitmap.CompressFormat.PNG,100,bos)
//            return bos.toByteArray()
//        }
//        return null
//    }


    actual fun getWidth(): Int {
        val image = mImage
        if(image != null){
            return image.size.useContents {
                return@useContents this.width.toInt()
            }

        }
        return 0
    }

    actual fun getHeight(): Int {
        val image = mImage
        if(image != null){
            return image.size.useContents {
                return@useContents this.width.toInt()
            }
        }
        return 0
    }

    actual fun toData(): ByteArray? {
        val image = mImage
        if(image != null){
            val data = UIImagePNGRepresentation(image)
            if(data != null){
                val length = data.length.toInt()
                val bytes = arrayOfNulls<Byte>(length)
                val cBytes = data.bytes
                if(cBytes != null){
                    memScoped {
                        val point = cBytes?.getPointer(this) as CPointer<ByteVar>
                        for ( i in 0 until length){
                            bytes[i] = point.get(i)
                        }
                    }
                }
                return (bytes as Array<Byte>).toByteArray()
            }
        }
        return null
    }

    actual fun release() {
    }
}