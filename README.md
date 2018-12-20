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
* [`concat`](#concat)
* [`deepFlatten`](#deepflatten)
* [`difference`](#difference)
* [`differenceBy`](#differenceby)
* [`differenceWith`](#differencewith)
* [`drop`](#drop)
* [`dropRight`](#dropright)
* [`dropRightWhile`](#droprightwhile)
* [`dropWhile`](#dropwhile)
* [`everyNth`](#everynth)
* [`filterNonUnique`](#filternonunique)
* [`filterNonUniqueBy`](#filternonuniqueby)
* [`findLast`](#findlast)
* [`findLastIndex`](#findlastindex)
* [`forEachRight`](#foreachright)
* [`groupBy`](#groupby)
* [`hasDuplicates`](#hasDuplicates)
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
    require(list.size == filter.size)
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

### countBy

Groups the elements of a list based on the given function and returns the count of elements in each group.

```kotlin
fun <T, K> countBy(list: List<T>, function: (T) -> K): Map<K, Int> =
    list.groupingBy(function).eachCount()
```

### countOccurrences

Counts the occurrences of a value in a list.

```kotlin
fun <T> countOccurrences(list: List<T>, target: T): Int =
    list.count { it == target }
```

### concat

Concatenates multiple lists into a single list, preserving the order of the passed in elements.

```kotlin
fun <T> concat(first: List<T>, vararg others: List<T>): List<T> =
    first.asSequence().plus(others.asSequence().flatten()).toList()
```

### difference

Returns a list of elements contained in the first list that are not present in the second list. 

```kotlin
fun <T> difference(first: List<T>, second: List<T>): List<T> =
    with(second.toSet()) {
        first.filterNot { contains(it) }
    }
```

### differenceBy

Returns a list of elements contained in the first list that are not present in the second list, after applying the provided function to each list element of both.

```kotlin
fun <T, U> differenceBy(first: List<T>, second: List<T>, function: (T) -> U): List<T> =
    with(second.toSet().map(function)) {
        first.filterNot { contains(function(it)) }
    }
```

### differenceWith

Filters out all elements from the first list for which the comparator function does not return `true` for that element and every element in the second list.

```kotlin
fun <T> differenceWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    first.filter { a -> second.none { b -> function(a, b) } }
```

### distinct

Returns all distinct elements.

```kotlin
fun <T> distinct(list: List<T>): List<T> =
    list.distinct()
```

### drop

Returns a new list with `n` elements removed from the left.

```kotlin
fun <T> drop(list: List<T>, n: Int): List<T> =
    list.drop(n)
```

### dropRight

Returns a new list with `n` elements removed from the right.

```kotlin
fun <T> dropRight(list: List<T>, n: Int): List<T> =
    list.dropLast(n)
```

### dropRightWhile

Removes elements from the end of a list until the passed function returns `true`. Returns the remaining elements in the list.

```kotlin
fun <T> dropRightWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.dropLastWhile(predicate)
```

### dropWhile

Removes elements from the beginning of a list until the passed function returns `true`. Returns the remaining elements in the list.

```kotlin
fun <T> dropWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.dropWhile(predicate)
```

### everyNth

Returns every nth element in a list.

```kotlin
fun <T> everyNth(list: List<T>, nth: Int): List<T> =
    list.windowed(nth, nth, partialWindows = false).map { it.last() }
```

### filterNonUnique

Filters out the non-unique values in a list.

```kotlin
fun <T> filterNonUnique(list: List<T>): List<T> =
    list.toSet().toList()
```

### filterNonUniqueBy

Filters out the non-unique values in an list, based on a provided comparator function.

```kotlin
fun <T> filterNonUniqueBy(list: List<T>, function: (T, T) -> Boolean): List<T> =
    list.filter { a -> list.none { b -> function(a, b) } }
```

### findLast

Returns the last element for which the provided function returns true, or null if none is found.

```kotlin
fun <T> findLast(list: List<T>, predicate: (T) -> Boolean): T? =
    list.findLast(predicate)
```

### findLastIndex

Returns the index of the last element for which the provided function returns true, or -1 if none is found.

```kotlin
fun <T> findLastIndex(list: List<T>, predicate: (T) -> Boolean): Int =
    list.indexOfLast(predicate)
```

### forEachRight

Executes a provided function once for each list element, starting from the list's last element.

```kotlin
fun <T> forEachRight(list: List<T>, action: (T) -> Unit): Unit =
    list.reversed().forEach(action)
```

### groupBy

Groups the elements of an list based on the given function.

```kotlin
fun <T, K> groupBy(list: List<T>, function: (T) -> K): Map<K, List<T>> =
    list.groupBy(function)
```

### hasDuplicates

Returns true if duplicate values exist in list, false otherwise.

```kotlin
fun <T> hasDuplicates(list: List<T>): Boolean =
    list.toSet().size != list.size
```

### head

Returns the head of a list.

```kotlin
fun <T> head(list: List<T>): T =
    list.first()
```

## License

MIT