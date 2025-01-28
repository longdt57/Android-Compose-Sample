package leegroup.module.coreimage.support.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.toBitmap(): Bitmap {
    val decodedString: ByteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}
