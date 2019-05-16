package com.hitales.utils

import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream


class FileUitls{

    companion object {

        fun readZipFile(file:File,block:((zipFile:ZipFile,zipEntity:ZipEntry)->Unit)){
            if(file.exists()){
                val zf = ZipFile(file)
                val zin = ZipInputStream(FileInputStream(file))
                var ze:ZipEntry? = zin.nextEntry
                while ( ze != null ){
                    block(zf,ze)
                    ze = zin.nextEntry
                }
                zin.close()
            }
        }

        fun traversingFile(file: File,block:((file:File)->Unit)){
            if(file.isDirectory){
                file.listFiles().forEach{
                    if(it.isDirectory){
                        traversingFile(it,block)
                    }else{
                        block(it)
                    }
                }

            }
        }



    }
}