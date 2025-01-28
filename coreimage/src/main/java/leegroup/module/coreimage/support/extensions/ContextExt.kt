package leegroup.module.coreimage.support.extensions

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.annotation.RawRes
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import leegroup.module.coreimage.R
import kotlin.coroutines.resume

suspend fun Context.loadBitmapFromUri(uri: Uri?): Bitmap? {
    return suspendCancellableCoroutine { continuation ->
        val request = ImageRequest.Builder(this)
            .data(uri)
            .target(
                onSuccess = {
                    continuation.resume(
                        (it as BitmapDrawable).bitmap.copy(
                            Bitmap.Config.ARGB_8888,
                            false,
                        ),
                    )
                },
                onError = {
                    continuation.resume(null)
                },
            )
            .build()
        imageLoader.enqueue(request)
    }
}

fun Context.sharePdf(fileUri: Uri, title: String = getString(R.string.share_pdf)) {
    val uri = FileProvider.getUriForFile(this, "${packageName}.provider", fileUri.toFile())

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, title))
}

fun Context.getRawUri(@RawRes id: Int, appId: String = packageName): Uri {
    return Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(appId)
        .appendEncodedPath(id.toString()).build()
}
