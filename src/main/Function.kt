import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

fun <T> allOf(vararg predicates: (T) -> Boolean): (T) -> Boolean =
    { t -> predicates.all { it(t) } }

infix fun <T, U, R> ((T) -> U).andThen(after: (U) -> R): (T) -> R = { after(this(it)) }

fun <T> anyOf(vararg predicates: (T) -> Boolean): (T) -> Boolean =
    { t -> predicates.any { it(t) } }

fun <T, U, R> applyFirst(function: (T) -> (U) -> R, first: T): (U) -> R = function(first)

fun <T, U, R> applySecond(function: (T) -> (U) -> R, second: U): (T) -> R = { t -> function(t)(second) }

infix fun <T, U, V> ((U) -> V).compose(before: (T) -> U): (T) -> V = { this(before(it)) }

fun <T, R> constant(result: R): (T) -> R = { result }

fun <T, U, R> ((T, U) -> R).curry(): (T) -> (U) -> R = { t -> { u -> this(t, u) } }

fun <T, R> delay(time: Int, timeUnit: TimeUnit = TimeUnit.SECONDS, function: (T) -> R): (T) -> R = TODO()

fun <T, R> diverge(t: T, predicate: (T) -> Boolean, onSuccess: (T) -> R, onFailure: (T) -> R): R =
    if (predicate(t)) onSuccess(t) else onFailure(t)

fun <T> identity(): (T) -> T = { it }

fun <T, U, R> lift(function: (T) -> (U) -> R): (Result<T>) -> (Result<U>) -> Result<R> =
    { resultT -> { resultU -> resultT.mapCatching(function).mapCatching { resultU.map(it) }.mapCatching { it.getOrThrow() } } }

fun <T, R> memoize(function: (T) -> R): (T) -> R =
    with(ConcurrentHashMap<T, R>()) {
        { t -> computeIfAbsent(t) { function(it) } }
    }

fun <T> noneOf(vararg predicates: (T) -> Boolean): (T) -> Boolean =
    { t -> !predicates.any { it(t) } }

fun <T, R> periodic(time: Int, timeUnit: TimeUnit = TimeUnit.SECONDS, function: (T) -> R): (T) -> Sequence<R> = TODO()

fun <T, R> retry(times: Int, delay: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, function: (T) -> R): (T) -> Result<R> {
    tailrec fun retry(input: T, result: Result<R>, times: Int): () -> Result<R> {
        if (result.isSuccess || times <= 0) {
            return { result }
        } else {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(delay.toLong(), timeUnit))
            return retry(input, runCatching { function(input) }, times - 1)
        }
    }
    return { t -> retry(t, runCatching { function(t) }, times - 1)() }
}

fun <T, R> sequence(list: List<(T) -> R>): (T) -> List<R> = { t -> list.map { it(t) } }

fun <T, U, R> ((T) -> (U) -> R).swapArgs(): (U) -> (T) -> R = { u -> { t -> this(t)(u) } }

fun <R> time(timeUnit: TimeUnit = TimeUnit.NANOSECONDS, function: () -> R): Pair<Long, Result<R>> {
    val start = System.nanoTime()
    val result = runCatching(function)
    val time = System.nanoTime() - start
    return timeUnit.convert(time, TimeUnit.NANOSECONDS) to result
}

fun <T, U, R> ((T) -> (U) -> R).uncurry(): (T, U) -> R = { t, u -> this(t)(u) }

fun <T, U, R> unlift(function: (Result<T>) -> (Result<U>) -> Result<R>): (T) -> (U) -> R =
    { t -> { u -> function(Result.success(t))(Result.success(u)).getOrThrow() } }
