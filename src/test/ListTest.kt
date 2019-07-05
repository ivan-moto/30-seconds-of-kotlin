import io.kotlintest.properties.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.random.Random

class ListTest: StringSpec() {

    init {

        "bifurcate - total size of lists returned equals size of each input list" {
            val listSize = 100
            val list: List<Int> = (0 until listSize).map { Random.nextInt() }
            val filter: List<Boolean> = (0 until listSize).map { Random.nextBoolean() }

            val result: Pair<List<Int>, List<Int>> = bifurcate(list, filter)

            result.first.size + result.second.size shouldBe list.size
            result.first.size + result.second.size shouldBe filter.size
        }

        "countBy - sum of ints returned equals size of input list" {
            forAll { list: List<String> ->
                countBy(list) { it.length }.values.sum() == list.size }
            }

        "concat - returned list preserves order of all elements" {
            forAll { first: List<Int>, second: List<Int>, third: List<Int> ->
                concat(first, second, third) == (first + second + third)
            }
        }

        "cycle - given list x, consecutive x sized chunks of returned sequence should each equal x" {
            forAll { list: List<String> ->
                cycle(list).take(1_000).chunkedThatHandlesSize0(list.size).all { it == list.take(it.size) }
            }
        }
        
        "difference - returned list returns nothing contained in second list" {
            forAll { first: List<String>, second: List<String> ->
                difference(first, second).none { second.contains(it) }
            }
        }

        "reduceSuccessive" {
            reduceSuccessive(listOf('H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l', 'd', '!'), "", String::plus)
                .shouldBe(listOf("H", "He", "Hel", "Hell", "Hello", "Hello,", "Hello, ", "Hello, W", "Hello, Wo", "Hello, Wor", "Hello, Worl", "Hello, World", "Hello, World!"))
        }
    }

    private fun <T> Sequence<T>.chunkedThatHandlesSize0(size: Int): Sequence<List<T>> =
        if (size == 0) emptySequence() else chunked(size)
}
