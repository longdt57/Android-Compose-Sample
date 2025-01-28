package leegroup.module.coreimage.support.extensions

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.toFormData(partName: String) = MultipartBody.Part.createFormData(
    partName,
    this.name,
    this.asRequestBody("image/*".toMediaType())
)
