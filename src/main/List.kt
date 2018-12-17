fun <T> all(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.all(predicate)

fun <T> allEqual(list: List<T>): Boolean =
    if (list.isEmpty()) false else list.all { it == list[0] }

fun <T> any(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.any(predicate)

fun <T> bifurcate(list: List<T>, filter: List<Boolean>): Pair<List<T>, List<T>> {
    if (list.size != filter.size) throw IllegalArgumentException()

    return list.zip(filter).partition { it.second }
        .let { (list1, list2) -> list1.map { it.first } to list2.map { it.first } }
}

fun <T> bifurcateBy(list: List<T>, predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
        list.partition(predicate)

fun <T> chunk(list: List<T>, size: Int): List<List<T>> =
        list.chunked(size)

fun <T> compact(list: List<T?>): List<T> {
    fun isTruthy(t: T?): Boolean = when(t) {
        null -> false
        is Boolean -> t
        is Double -> t != Double.NaN
        is Number -> t.toInt() != 0
        is String -> !t.isEmpty()
        is Collection<*> -> !t.isEmpty()
        else -> true
    }
    @Suppress("UNCHECKED_CAST")
    return list.filter(::isTruthy) as List<T>
}
