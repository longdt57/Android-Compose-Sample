package leegroup.module.coreimage.file

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class ExternalFileSource(
    private val context: Context,
) {

    fun saveFileToDocuments(uri: Uri) {
        val pdfFile = uri.toFile()
        val fileName = pdfFile.name // Get the file name from the File object

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use MediaStore API for Android 10 and above
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
            }

            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
            uri?.let {
                try {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        FileInputStream(pdfFile).use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {
            // For older versions, use the traditional file system
            val documentsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            if (!documentsDir.exists()) {
                documentsDir.mkdirs()
            }
            val destinationFile = File(documentsDir, fileName)
            try {
                FileInputStream(pdfFile).use { inputStream ->
                    FileOutputStream(destinationFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}