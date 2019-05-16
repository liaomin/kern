package com.hitales.utils

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val dir = "/Users/liaomin/.gradle/caches/modules-2/files-2.1"
    val dirFile = File(dir)
    val outDir = "/Users/liaomin/Documents/ideaProject/Kotlin/Android/SharedCode/androidx"
    val outDirFile = File(outDir)
    dirFile.listFiles().filter {
        it.name.startsWith("androidx")
    }.forEach {
        val packageName = it.name
        if(it.isDirectory){
            val subFile = it.listFiles().filter { it.name.equals("27.1.1") }
            subFile.forEach(){
                FileUitls.traversingFile(it){
                    if(it.name.endsWith("aar")){
                        FileUitls.readZipFile(it){zipFile,zipEntity ->
                            if(zipEntity.name.equals("classes.jar")){
                                val outFile = File(outDirFile,"$packageName.jar")
                                val inputStream =  BufferedInputStream(zipFile.getInputStream(zipEntity))
                                val outputStream = FileOutputStream(outFile)
                                outputStream.write(inputStream.readBytes())
                                outputStream.flush()
                                outputStream.close()
                                inputStream.close()
                            }

                        }
                    }
                }
            }
        }
        FileUitls.traversingFile(it){
            if(it.name.endsWith("aar")){
                val aarFile = it
                FileUitls.readZipFile(it){zipFile,zipEntity ->
                    if(zipEntity.name.equals("classes.jar")){
                        val outFile = File(outDirFile,aarFile.name.replace("aar","jar"))
                        val inputStream =  BufferedInputStream(zipFile.getInputStream(zipEntity))
                        val outputStream = FileOutputStream(outFile)
                        outputStream.write(inputStream.readBytes())
                        outputStream.flush()
                        outputStream.close()
                        inputStream.close()
                    }

                }
            }

//            val sourceDir = File(outDirFile,"source")
//            if(it.name.endsWith("jar")){
//                val outFile = File(sourceDir,it.name)
//                outFile.parentFile.mkdirs()
//                val inputStream =  BufferedInputStream(FileInputStream(it))
//                val outputStream = FileOutputStream(outFile)
//                outputStream.write(inputStream.readBytes())
//                outputStream.flush()
//                outputStream.close()
//                inputStream.close()
//            }
        }
    }

}