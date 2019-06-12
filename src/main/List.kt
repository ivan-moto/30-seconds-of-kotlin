import kotlin.math.max

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

fun <T> combinations(list: List<T>): List<List<T>> = TODO()

fun <T> compact(list: List<T?>): List<T> {
    fun isTruthy(t: T?): Boolean = when(t) {
        null -> false
        is Boolean -> t
        is Double -> t != Double.NaN
        is Number -> t.toInt() != 0
        is String -> !t.isEmpty()
        is Array<*> -> t.size != 0
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

fun <T, U> corresponds(first: List<T>, second: List<U>, predicate: (T, U) -> Boolean): Boolean =
    (first.size == second.size) && (first.zip(second).all { (t, u) -> predicate(t, u) })

fun <T, U> crossProduct(first: List<T>, second: List<U>): List<Pair<T, U>> =
    first.flatMap { a -> second.map { b -> a to b } }

fun <T> difference(first: List<T>, second: List<T>): List<T> =
    (first subtract second).toList()

fun <T, R> differenceBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> =
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

fun <T> endsWith(list: List<T>, subList: List<T>): Boolean =
    list.takeLast(subList.size) == subList

fun <T> everyNth(list: List<T>, nth: Int): List<T> =
    list.windowed(nth, nth, partialWindows = false).map { it.last() }

fun <T> existsUnique(list: List<T>, predicate: (T) -> Boolean): Boolean {
    var exists = false
    for (t in list) {
        if (predicate(t)) {
            if (exists) {
                return false
            } else {
                exists = true
            }
        }
    }
    return exists
}

fun <T> filterNonUnique(list: List<T>): List<T> =
    list.distinct()

fun <T, K> filterNonUniqueBy(list: List<T>, function: (T) -> K): List<T> =
    list.distinctBy(function)

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

tailrec fun <T> hasSubList(list: List<T>, subList: List<T>): Boolean =
    when {
        subList.isEmpty() -> true
        list.isEmpty() -> subList.isEmpty()
        list.take(subList.size) == subList -> true
        else -> hasSubList(list.drop(1), subList)
    }

fun <T> head(list: List<T>): T =
    list.first()

fun <T> indexOfAll(list: List<T>, target: T): List<Int> =
    list.withIndex().filter { it.value == target }.map { it.index }

fun <T> initial(list: List<T>): List<T> =
    list.dropLast(1)

fun <T> initialize2DList(width: Int, height: Int, value: T): List<List<T>> =
    List(height) { List(width) { value } }

fun initializeListWithRange(start: Int, stop: Int, step: Int): List<Int> =
    (start..stop step step).toList()

fun <T> initializeListWithValue(size: Int, value: T): List<T> =
    List(size) { value }

fun <T> intersection(first: List<T>, second: List<T>): List<T> =
    (first intersect second).toList()

fun <T, R> intersectionBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> =
    with(second.toSet().map(function)) {
        first.filter { contains(function(it)) }
    }

fun <T> intersectionWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    first.filter { a -> second.any { b -> function(a, b) } }

fun <T> intersperse(list: List<T>, element: T): List<T> =
    List(list.size) { index -> listOf(list[index], element) }.flatten().dropLast(1)

fun <T> join(list: List<T>, separator: String = ", "): String =
    list.joinToString(separator)

fun <T> last(list: List<T>): T =
    list.last()

fun <T> longest(list: List<Collection<T>>): Collection<T>? =
    list.maxBy { it.size }

fun <T, R> mapObject(list: List<T>, function: (T) -> R): Map<T, R> =
    list.associateWith(function)

fun <T : Comparable<T>> maxN(list: List<T>, n: Int): List<T> =
    list.sortedDescending().take(n)

fun <T : Comparable<T>> minN(list: List<T>, n: Int): List<T> =
    list.sorted().take(n)

fun <T> none(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.none(predicate)

fun <T> nthElement(list: List<T>, n: Int): T =
   list[n]

fun <T> permutations(list: List<T>): List<List<T>> {
    fun <T> List<T>.removeAtIndex(index: Int): List<T> = take(index) + drop(index + 1)
    fun <T> List<T>.prepend(element: T): List<T> = listOf(element) + this
    return  when {
        list.isEmpty() -> emptyList()
        list.size == 1 -> listOf(list)
        else -> list.foldIndexed(mutableListOf()) { index, acc, t ->
            acc.apply {
                addAll(permutations(list.removeAtIndex(index)).map { it.prepend(t) })
            }
        }
    }
}

fun <T> partition(list: List<T>, predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    list.partition(predicate)

fun <T> partitioningBy(list: List<T>, predicate: (T) -> Boolean): Map<Boolean, List<T>> = 
    list.groupBy(predicate)

fun <T, U, R> product(first: List<T>, second: List<U>, function: (T, U) -> R): List<R> =
    first.flatMap { t -> second.map { u -> function(t, u) } }

fun <T> pull(list: List<T>, vararg elements: T): List<T> =
    with(elements.toSet()) {
       list.filterNot { contains(it) }
    }

fun <T> pullAtIndex(list: List<T>, vararg indices: Int): List<T> =
    with(indices.toSet()) {
        list.filterIndexed { index, _ -> !contains(index) }
    }

fun <T> pullAtValue(list: List<T>, vararg elements: T): List<T> =
    with(elements.toSet()) {
        list.filter { contains(it) }
    }

fun <T, R> reduceSuccessive(list: List<T>, identity: R, function: (R, T) -> R): List<R> {
    fun <T> List<T>.lastOrElse(t: T): T = lastOrNull() ?: t
    return list.fold(emptyList()) { acc, t -> acc + function(acc.lastOrElse(identity), t) }
}

fun <T> reject(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.filterNot(predicate)

fun <T> remove(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.filter(predicate)

fun <T> rotateLeft(list: List<T>, n: Int): List<T> =
    list.drop(n % list.size) + list.take(n % list.size)

fun <T> rotateRight(list: List<T>, n: Int): List<T> =
    list.takeLast(n % list.size) + list.dropLast(n % list.size)

fun <T> sample(list: List<T>): T =
    list.random()

fun <T> sampleSize(list: List<T>, n: Int): List<T> =
    list.shuffled().take(n)

fun <T> segmentLength(list: List<T>, predicate: (T) -> Boolean): Int =
    list.fold(0 to 0) { (longest, current), t -> if (predicate(t)) longest to current + 1 else max(longest, current) to 0 }.first

fun <T> shank(list: List<T>, start: Int = 0, deleteCount: Int = 0, vararg elements: T): List<T> =
    list.slice(0 until start) + elements + list.drop(start + deleteCount)

fun <T> shuffle(list: List<T>): List<T> =
    list.shuffled()

fun <T, R> slideBy(list: List<T>, classifier: (T) -> R): List<List<T>> {
    tailrec fun slideBy_(list: List<T>, acc: MutableList<List<T>>): MutableList<List<T>> =
        if (list.isEmpty())
            acc
        else
            slideBy_(list.dropWhile { classifier(it) == classifier(list.first()) },  acc.apply { add(list.takeWhile { classifier(it) == classifier(list.first()) } )} )
    return slideBy_(list, mutableListOf())
}

fun <T> segmentLength1(list: List<T>, predicate: (T) -> Boolean): Int =
    list.windowed(max(list.size, 1), partialWindows = true)
        .map { it.takeWhile(predicate).size }
        .max() ?: 0

fun <T : Comparable<T>> sortOrder(list: List<T>): Int =
    with(list.sorted()) {
        when {
            this == list ->  1
            this.asReversed() == list -> -1
            else -> 0
        }
    }

fun <T> span(list: List<T>, predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    list.takeWhile(predicate) to list.dropWhile(predicate)

fun <T> splitAt(list: List<T>, predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    list.takeWhile { !predicate(it) } to list.dropWhile { !predicate(it) }

fun <T> startsWith(list: List<T>, subList: List<T>): Boolean =
    list.take(subList.size) == subList

fun <T> symmetricDifference(first: List<T>, second: List<T>): List<T> =
    ((first subtract second) + (second subtract first)).toList()

fun <T, R> symmetricDifferenceBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> {
    val mapFirst = first.toSet().map(function)
    val mapSecond = second.toSet().map(function)
    return first.filterNot { mapSecond.contains(function(it)) } + second.filterNot { mapFirst.contains(function(it)) }
}

fun <T> symmetricDifferenceWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    first.filter { a -> second.none { b -> function(a ,b) } } +
        second.filter { b -> first.none { a -> function(a, b) } }

fun <T> tail(list: List<T>): List<T> =
    list.drop(1)

fun <T> take(list: List<T>, n: Int): List<T> =
    list.take(n)

fun <T> takeRight(list: List<T>, n: Int): List<T> =
    list.takeLast(n)

fun <T> takeRightWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.takeLastWhile(predicate)

fun <T> takeWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.takeWhile(predicate)

fun <T> union(first: List<T>, second: List<T>): List<T> =
    (first union second).toList()

fun <T, R> unionBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> {
    val mapFirst = first.toSet().map(function)
    return (first.toSet() + second.toSet().filterNot { mapFirst.contains(function(it)) }).toList()
}

fun <T> unionWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    (first.filter { a -> second.any { b -> function(a, b) } } union
            second.filter { b -> first.any { a -> function(a, b) } }).toList()

fun <T, U> unzip(list: List<Pair<T, U>>): Pair<List<T>, List<U>> =
    list.unzip()

fun <T, U> zip(first: List<T>, second: List<U>): List<Pair<T, U>> =
    first.zip(second)

fun <T, U> zipAll(first: List<T>, defaultT: T, second: List<U>, defaultU: U): List<Pair<T, U>> {
    val firstIt = first.iterator()
    val secondIt = second.iterator()
    return object : Iterator<Pair<T, U>> {
        override fun hasNext(): Boolean =
            firstIt.hasNext() || secondIt.hasNext()

        override fun next(): Pair<T, U> {
            val t = if (firstIt.hasNext()) firstIt.next() else defaultT
            val u = if (secondIt.hasNext()) secondIt.next() else defaultU
            return t to u
        }
    }.asSequence().toList()
}

fun <K, V> zipKeysValues(keys: List<K>, values: List<V>): Map<K, V> =
    keys.zip(values).toMap()

fun <T, U, R> zipWith(first: List<T>, second: List<U>, function: (T, U) -> R): List<R> =
    first.zip(second).map { (t, u) -> function(t, u) }

fun <T> zipWithIndex(list: List<T>): List<Pair<Int, T>> =
    list.withIndex().map { it.index to it.value }

fun <T> zipWithNext(list: List<T>): List<Pair<T, T>> =
    list.zipWithNext()
