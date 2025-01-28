package leegroup.module.coreimage.support.extensions

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap?.toBase64String(
    compress: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this?.compress(compress, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun Bitmap?.recycleOrNothing() {
    if (this?.isRecycled == false) {
        this.recycle()
    }
}
