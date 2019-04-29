package com.hitales.ui


actual class Image {
//
//    var bitmap:Bitmap? = null
//
//    constructor(bitmap: Bitmap){
//        this.bitmap = bitmap
//    }
//
//    actual private constructor()
//
//    actual companion object {
//
//        actual fun named(name: String): Image? {
//            var inputStream:InputStream? = null
//            try {
//                inputStream = Platform.getApplication().assets.open(name)
//                return Image(BitmapFactory.decodeStream(inputStream))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }finally {
//                if(inputStream != null){
//                    try {
//                        inputStream.close()
//                    }catch (e:Exception){
//
//                    }
//                }
//            }
//            return null
//        }
//
//    }
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
    actual companion object {
        actual fun named(name: String): Image? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private actual constructor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getWidth(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getHeight(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun toData(): ByteArray? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}