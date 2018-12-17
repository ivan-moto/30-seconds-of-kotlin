<img src="media/logo.png" alt="Kotlin">

# 30 seconds of Kotlin

> Curated collection of useful Kotlin 1.3 snippets that you can understand quickly.

## Table of Contents

Note: This project is inspired by, but in no way affiliated with, [30 Seconds of Code](https://github.com/30-seconds/30-seconds-of-code).

### List

<details>
<summary>View contents</summary>

* [`all`](#all)
* [`allEqual`](#allequal)
* [`any`](#any)
* [`bifurcate`](#bifurcate)
* [`bifurcateBy`](#bifurcateby)
* [`chunk`](#chunk)
* [`compact`](#compact)
* [`countBy`](#countby)
* [`countOccurrences`](#countoccurrences)
* [`deepFlatten`](#deepflatten)
* [`difference`](#difference)
* [`differenceBy`](#differenceby)
* [`differenceWith`](#differencewith)
* [`drop`](#drop)
* [`dropRight`](#dropright)
* [`dropRightWhile`](#droprightwhile)
* [`dropWhile`](#dropwhile)
* [`everyNth`](#everynth)
* [`filterFalsy`](#filterfalsy)
* [`filterNonUnique`](#filternonunique)
* [`filterNonUniqueBy`](#filternonuniqueby)
* [`findLast`](#findlast)
* [`findLastIndex`](#findlastindex)
* [`flatten`](#flatten)
* [`forEachRight`](#foreachright)
* [`groupBy`](#groupby)
* [`head`](#head)
* [`indexOfAll`](#indexofall)
* [`initial`](#initial)
* [`initialize2DArray`](#initialize2darray)
* [`initializeArrayWithRange`](#initializearraywithrange)
* [`initializeArrayWithRangeRight`](#initializearraywithrangeright)
* [`initializeArrayWithValues`](#initializearraywithvalues)
* [`initializeNDArray`](#initializendarray)
* [`intersection`](#intersection)
* [`intersectionBy`](#intersectionby)
* [`intersectionWith`](#intersectionwith)
* [`isSorted`](#issorted)
* [`join`](#join)
* [`JSONtoCSV`](#jsontocsv-)
* [`last`](#last)
* [`longestItem`](#longestitem)
* [`mapObject`](#mapobject-)
* [`maxN`](#maxn)
* [`minN`](#minn)
* [`none`](#none)
* [`nthElement`](#nthelement)
* [`offset`](#offset)
* [`partition`](#partition)
* [`permutations`](#permutations-)
* [`pull`](#pull)
* [`pullAtIndex`](#pullatindex-)
* [`pullAtValue`](#pullatvalue-)
* [`pullBy`](#pullby-)
* [`reducedFilter`](#reducedfilter)
* [`reduceSuccessive`](#reducesuccessive)
* [`reduceWhich`](#reducewhich)
* [`reject`](#reject)
* [`remove`](#remove)
* [`sample`](#sample)
* [`sampleSize`](#samplesize)
* [`shank`](#shank)
* [`shuffle`](#shuffle)
* [`similarity`](#similarity)
* [`sortedIndex`](#sortedindex)
* [`sortedIndexBy`](#sortedindexby)
* [`sortedLastIndex`](#sortedlastindex)
* [`sortedLastIndexBy`](#sortedlastindexby)
* [`stableSort`](#stablesort-)
* [`symmetricDifference`](#symmetricdifference)
* [`symmetricDifferenceBy`](#symmetricdifferenceby)
* [`symmetricDifferenceWith`](#symmetricdifferencewith)
* [`tail`](#tail)
* [`take`](#take)
* [`takeRight`](#takeright)
* [`takeRightWhile`](#takerightwhile)
* [`takeWhile`](#takewhile)
* [`toHash`](#tohash)
* [`union`](#union)
* [`unionBy`](#unionby)
* [`unionWith`](#unionwith)
* [`uniqueElements`](#uniqueelements)
* [`uniqueElementsBy`](#uniqueelementsby)
* [`uniqueElementsByRight`](#uniqueelementsbyright)
* [`uniqueSymmetricDifference`](#uniquesymmetricdifference)
* [`unzip`](#unzip)
* [`unzipWith`](#unzipwith-)
* [`without`](#without)
* [`xProd`](#xprod)
* [`zip`](#zip)
* [`zipObject`](#zipobject)
* [`zipWith`](#zipwith-)

</details>

---

## List

### all

Returns `true` if the provided predicate function returns `true` for all elements in a list, `false` otherwise.

```kotlin
fun <T> all(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.all(predicate)
```

## allEqual

Check if all elements in a list are equal.

```kotlin
fun <T> allEqual(list: List<T>): Boolean =
    if (list.isEmpty()) false else list.all { it == list[0] }
```

### any

Returns `true` if the provided predicate function returns `true` for at least one element in a list, `false` otherwise.

```kotlin
fun <T> any(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.any(predicate)
```

### bifurcate

Splits values into two groups. For every element in a list, if the corresponding boolean in another list is true, add the element to the first group; otherwise, add it to the second group. 

```kotlin
fun <T> bifurcate(list: List<T>, filter: List<Boolean>): Pair<List<T>, List<T>> {
    if (list.size != filter.size) throw IllegalArgumentException()

    return list.zip(filter).partition { it.second }
        .let { (list1, list2) -> list1.map { it.first } to list2.map { it.first } }
}
```

### bifurcateBy

Splits values into two groups according to a predicate function, which specifies which group an element in the input list belongs to. If the predicate function returns true, the list element belongs to the first group; otherwise, it belongs to the second group.

```kotlin
fun <T> bifurcateBy(list: List<T>, predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
        list.partition(predicate)
```

### chunk

Chunks a list into smaller lists of a specified size.

```kotlin
fun <T> chunk(list: List<T>, size: Int): List<List<T>> =
        list.chunked(size)
```

### compact

Removes "falsey" values from a list.

Kotlin doesn't distinguish falsey values but they are (`false`, `null`, `0`, `""`, `empty collection`, and `NaN`).

```kotlin
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
```

## License

MIT