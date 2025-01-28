package leegroup.module.coreimage.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class ContactPermissionChecker(private val context: Context) : PermissionChecker {

    override fun requestPermission(
        arePermissionsGranted: (Boolean) -> Unit,
        onPermissionPermanentlyDenied: () -> Unit
    ) {
        if (this.isPermissionGranted()) {
            arePermissionsGranted(true)
            return
        }

        Dexter.withContext(context)
            .withPermissions(getPermissions())
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.isAnyPermissionPermanentlyDenied) {
                            onPermissionPermanentlyDenied()
                        }
                        arePermissionsGranted(report.areAllPermissionsGranted())
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

    override fun isPermissionGranted(): Boolean {
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

    override fun getPermissions(): List<String> {
        return listOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
        )
    }
}
