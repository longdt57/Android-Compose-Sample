package leegroup.module.photosample.support.extensions

fun String.convertToDummyUrl(): String {
    return replace("https://via.placeholder.com", "https://dummyimage.com")
}