package leegroup.module.compose.support.extensions

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

fun createImageUri(context: Context): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(
            MediaStore.MediaColumns.DISPLAY_NAME,
            "captured_image_${System.currentTimeMillis()}.jpg"
        )
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraCapture")
    }
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

@Composable
fun rememberCameraCaptureState(): CameraCaptureState {
    val context = LocalContext.current
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher to capture a photo and save it to the provided URI
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (!success) {
                capturedImageUri = null // Reset if capture fails
            }
        }

    return CameraCaptureState(
        capturedImageUri = capturedImageUri,
        onCapture = {
            val uri = createImageUri(context)
            if (uri != null) {
                capturedImageUri = uri
                launcher.launch(uri)
            }
        }
    )
}

data class CameraCaptureState(
    val capturedImageUri: Uri?,
    val onCapture: () -> Unit
)
