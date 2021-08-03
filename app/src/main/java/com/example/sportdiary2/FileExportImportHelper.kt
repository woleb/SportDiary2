package com.example.sportdiary2

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileExportImportHelper (var context: Context)  {

    private val filepath="MyFileStorage"
    internal var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState=Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            true
        } else {
            false
        }
    }

    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            true
        } else{
            false
        }
    }

    //@RequiresApi(Build.VERSION_CODES.R)
    public fun ExportToFile () {
        val fileName = "database_backup.csv"
        val fileData = "test text"
        myExternalFile = File(Environment.getDataDirectory(), fileName)
        Log.d(myExternalFile.toString(), "filename")
        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(fileData.toByteArray())
            fileOutPutStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(context,"data save",Toast.LENGTH_SHORT).show()

    }

}