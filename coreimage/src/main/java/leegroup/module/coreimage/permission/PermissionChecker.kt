package leegroup.module.coreimage.permission

interface PermissionChecker {
    fun requestPermission(
        arePermissionsGranted: (Boolean) -> Unit,
        onPermissionPermanentlyDenied: () -> Unit
    )

    fun isPermissionGranted(): Boolean
    fun getPermissions(): List<String>
}