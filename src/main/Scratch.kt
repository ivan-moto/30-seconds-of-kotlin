import kotlin.math.floor
import kotlin.math.round

//
//fun <T> permutations(list: List<T>): List<List<T>> {
//    tailrec fun <T> permutations_(list: List<T>, acc: List<List<T>>): List<T> {
//        if (list.isEmpty()) return acc
//        val head: T = list.first()
//        return permutations_(list.drop(1), acc.map { it + head })
//    }
//    return list.toMutableList().let {
//            list -> list.foldIndexed(mutableListOf()) {
//            index, acc, t -> acc.apply { addAll(permutations_(list.apply { removeAt(index) })) }
//        .map { mutableListOf(list[index]) + it }
//    }
//    }
//}
//
//fun <T> permutations2(list: List<T>): List<List<T>> {
//    tailrec fun <T> permutations_(prefix: List<T>, remaining: List<T>, acc: MutableList<List<T>>): List<List<T>> =
//        when {
//            remaining.isEmpty() || remaining.size == 1 -> acc.apply { add(remaining) }
//            else -> permutations_(prefix + remaining.first(), remaining.drop(1))
//        }
//
//    return permutations_(emptyList(), list, MutableList(list.size) { emptyList<T>() })
//}
//
//fun <T> permutations3(list: List<T>): List<List<T>> {
//    tailrec fun <T> permutations_(prefix: List<T>, remaining: List<T>): List<T> =
//        when {
//            remaining.isEmpty() -> prefix
//            else -> permutations_(prefix + remaining.first(), remaining.drop(1))
//        }
//
//    return permutations_(emptyList(), list, MutableList(list.size) { emptyList<T>() })
//}

// List:

// Function:

// Lazy:

// Map: look at vavr functions

fun main() {
//    println(permutations(listOf(1, 2, 3)))
    println(permutations(listOf(1, 2, 3)))

}
