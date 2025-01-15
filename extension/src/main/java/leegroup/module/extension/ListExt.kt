package leegroup.module.extension

fun <T> List<T>.updateItemInList(
    updatedItem: T,
    areItemsEqual: (T, T) -> Boolean
): List<T> {
    return map { item ->
        if (areItemsEqual(item, updatedItem)) updatedItem else item
    }
}
