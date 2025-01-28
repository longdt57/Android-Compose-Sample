package leegroup.module.coreimage.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MediaFullPermissionChecker(private val context: Context) : PermissionChecker {

    override fun requestPermission(
        arePermissionsGranted: (Boolean) -> Unit,
        onPermissionPermanentlyDenied: () -> Unit
    ) {
        if (isPermissionGranted()) {
            arePermissionsGranted(true)
            return
        }

        Dexter.withContext(context)
            .withPermissions(getPermissions())
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        val isGranted =
                            isPermissionGranted() || isMediaImagePermissionGratedPartially()
                        arePermissionsGranted(isGranted)
                        if (isGranted.not() && report.isAnyPermissionPermanentlyDenied) {
                            onPermissionPermanentlyDenied()
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        token: PermissionToken?,
                    ) {
                        token?.continuePermissionRequest()
                    }
                },
            ).check()
    }

    override fun isPermissionGranted(): Boolean = isMediaImagePermissionGranted()

    private fun isMediaImagePermissionGranted(): Boolean {
        getPermissions().forEach { permission ->
            val isPermissionGranted = ActivityCompat.checkSelfPermission(
                context,
                permission,
            ) == PackageManager.PERMISSION_GRANTED
            if (isPermissionGranted.not()) {
                return false
            }
        }
        return true
    }

    fun isMediaImagePermissionGratedPartially(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun getPermissions(): List<String> {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE ->
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
                )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )

            else -> listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }
    }
}
