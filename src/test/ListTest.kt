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

        "difference - returned list returns nothing contained in second list" {
            forAll { first: List<String>, second: List<String> ->
                difference(first, second).filter { second.contains(it) }.isEmpty()
            }
        }

        "reduceSuccessive" {
            reduceSuccessive(listOf('H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l', 'd', '!'), "", String::plus)
                .shouldBe(listOf("H", "He", "Hel", "Hell", "Hello", "Hello,", "Hello, ", "Hello, W", "Hello, Wo", "Hello, Wor", "Hello, Worl", "Hello, World", "Hello, World!"))
        }
    }
}
