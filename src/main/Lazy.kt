fun <T> Lazy<T>.asSequence(): Sequence<T> = object : Iterator<T> {
        override fun hasNext(): Boolean = true
        override fun next(): T = value
    }.asSequence()

fun <T> Lazy<T>.filter(predicate: (T) -> Boolean): Lazy<Result<T>> =
    lazy { if (predicate(value)) Result.success(value) else Result.failure(IllegalArgumentException("Predicate evaluated to false.")) }

fun <T, R> Lazy<T>.flatMap(function: (T) -> Lazy<R>): Lazy<R> =
    lazy { function(value).value }

fun <R, T : R> Lazy<T>.getOrDefault(default: R): R =
    runCatching { value }.getOrDefault(default)

// this is named lift1 because of name conflicts with Function#lift
fun <T, U, R> lift1(function: (T) -> (U) -> R): (Lazy<T>) -> (Lazy<U>) -> Lazy<R> =
    { lazyT -> { lazyU -> lazy { function(lazyT.value)(lazyU.value) } } }

fun <T, R> Lazy<T>.map(function: (T) -> R): Lazy<R> =
    lazy { function(value) }

fun <T, U, R> map2(lazy1: Lazy<T>, lazy2: Lazy<U>, function: (T) -> (U) -> R): Lazy<R> =
    lazy { function(lazy1.value)(lazy2.value) }

fun <T> sequence(list: List<Lazy<T>>): Lazy<List<T>> =
    lazy { list.map { it.value } }

fun <T> sequenceCatching(list: List<Lazy<T>>): Lazy<Result<List<T>>> =
    lazy { runCatching { list.map { it.value } } }

fun <T> Lazy<T>.test(predicate: (T) -> Boolean): Lazy<Boolean> =
    lazy { predicate(value) }