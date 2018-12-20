fun <T> all(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.all(predicate)

fun <T> allEqual(list: List<T>): Boolean =
    if (list.isEmpty()) false else list.all { it == list[0] }

fun <T> any(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.any(predicate)

fun <T> bifurcate(list: List<T>, filter: List<Boolean>): Pair<List<T>, List<T>> {
    require(list.size == filter.size)
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

fun <T, K> countBy(list: List<T>, function: (T) -> K): Map<K, Int> =
    list.groupingBy(function).eachCount()

fun <T> countOccurrences(list: List<T>, target: T): Int =
    list.count { it == target }

fun <T> concat(first: List<T>, vararg others: List<T>): List<T> =
    first.asSequence().plus(others.asSequence().flatten()).toList()

fun <T> difference(first: List<T>, second: List<T>): List<T> =
    with(second.toSet()) {
        first.filterNot { contains(it) }
    }

fun <T, U> differenceBy(first: List<T>, second: List<T>, function: (T) -> U): List<T> =
    with(second.toSet().map(function)) {
        first.filterNot { contains(function(it)) }
    }

fun <T> differenceWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    first.filter { a -> second.none { b -> function(a, b) } }

fun <T> distinct(list: List<T>): List<T> =
    list.distinct()

fun <T> drop(list: List<T>, n: Int): List<T> =
    list.drop(n)

fun <T> dropRight(list: List<T>, n: Int): List<T> =
    list.dropLast(n)

fun <T> dropRightWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.dropLastWhile(predicate)

fun <T> dropWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.dropWhile(predicate)

fun <T> everyNth(list: List<T>, nth: Int): List<T> =
    list.windowed(nth, nth, partialWindows = false).map { it.last() }

fun <T> filterNonUnique(list: List<T>): List<T> =
    list.toSet().toList()

fun <T> filterNonUniqueBy(list: List<T>, function: (T, T) -> Boolean): List<T> =
    list.filter { a -> list.none { b -> function(a, b) } }

fun <T> findLast(list: List<T>, predicate: (T) -> Boolean): T? =
    list.findLast(predicate)

fun <T> findLastIndex(list: List<T>, predicate: (T) -> Boolean): Int =
    list.indexOfLast(predicate)

fun <T> forEachRight(list: List<T>, action: (T) -> Unit): Unit =
    list.reversed().forEach(action)

fun <T, K> groupBy(list: List<T>, function: (T) -> K): Map<K, List<T>> =
    list.groupBy(function)

fun <T> hasDuplicates(list: List<T>): Boolean =
    list.toSet().size != list.size

fun <T> head(list: List<T>): T =
    list.first()