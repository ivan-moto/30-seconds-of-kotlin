<img src="media/logo.png" alt="Kotlin">

# 30 seconds of Kotlin

> Curated collection of useful Kotlin 1.3 snippets that you can understand quickly, using only stdlib functionality.

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
* [`crossProduct`](#crossproduct)
* [`difference`](#difference)
* [`differenceBy`](#differenceby)
* [`differenceWith`](#differencewith)
* [`distinct`](#distinct)
* [`drop`](#drop)
* [`dropRight`](#dropright)
* [`dropRightWhile`](#droprightwhile)
* [`dropWhile`](#dropwhile)
* [`endsWith`](#endsWith)
* [`everyNth`](#everynth)
* [`filterNonUnique`](#filternonunique)
* [`filterNonUniqueBy`](#filternonuniqueby)
* [`findLast`](#findlast)
* [`findLastIndex`](#findlastindex)
* [`forEachRight`](#foreachright)
* [`groupBy`](#groupby)
* [`hasDuplicates`](#hasDuplicates)
* [`hasSubList`](#hassubList)
* [`head`](#head)
* [`indexOfAll`](#indexofall)
* [`initial`](#initial)
* [`initialize2DList`](#initialize2dlist)
* [`initializeListWithRange`](#initializelistwithrange)
* [`initializeListWithValue`](#initializelistwithvalue)
* [`intersection`](#intersection)
* [`intersectionBy`](#intersectionby)
* [`intersectionWith`](#intersectionwith)
* [`join`](#join)
* [`last`](#last)
* [`longest`](#longest)
* [`mapObject`](#mapobject)
* [`maxN`](#maxn)
* [`minN`](#minn)
* [`none`](#none)
* [`nthElement`](#nthelement)
* [`offset`](#offset)
* [`partition`](#partition)
* [`product`](#product)
* [`pull`](#pull)
* [`pullAtIndex`](#pullatindex)
* [`pullAtValue`](#pullatvalue)
* [`reduceSuccessive`](#reducesuccessive)
* [`reject`](#reject)
* [`remove`](#remove)
* [`sample`](#sample)
* [`sampleSize`](#samplesize)
* [`shank`](#shank)
* [`shuffle`](#shuffle)
* [`sortOrder`](#sortorder)
* [`startsWith`](#startswith)
* [`symmetricDifference`](#symmetricdifference)
* [`symmetricDifferenceBy`](#symmetricdifferenceby)
* [`symmetricDifferenceWith`](#symmetricdifferencewith)
* [`tail`](#tail)
* [`take`](#take)
* [`takeRight`](#takeright)
* [`takeRightWhile`](#takerightwhile)
* [`takeWhile`](#takewhile)
* [`union`](#union)
* [`unionBy`](#unionby)
* [`unionWith`](#unionwith)
* [`unzip`](#unzip)
* [`without`](#without)
* [`zip`](#zip)
* [`zipAll`](#zipall)
* [`zipKeysValues`](#zipkeysvalues)
* [`zipWith`](#zipwith)
* [`zipWithIndex`](#zipwithindex)
* [`zipWithNext`](#zipwithnext)

</details>

### Function

<details>
<summary>View contents</summary>

* [`allOf`](#allof)
* [`andThen`](#andthen)
* [`anyOf`](#anyof)
* [`applyFirst`](#applyfirst)
* [`applySecond`](#applysecond)
* [`compose`](#compose)
* [`constant`](#constant)
* [`curry`](#curry)
* [`diverge`](#diverge)
* [`identity`](#identity)
* [`lift`](#lift)
* [`memoize`](#memoize)
* [`noneOf`](#noneof)
* [`retry`](#retry)
* [`sequence`](#sequence)
* [`swapArgs`](#swapargs)
* [`time`](#time)
* [`uncurry`](#uncurry)
* [`unlift`](#unlift)

</details>

### Lazy

<details>
<summary>View contents</summary>

* [`asSequence`](#assequence)
* [`filter`](#filter)
* [`flatMap`](#flatmap)
* [`getOrDefault`](#getordefault)
* [`lift`](#lift)
* [`map`](#map)
* [`map2`](#map2)
* [`sequence`](#sequence)
* [`sequenceCatching`](#sequencecatching)
* [`test`](#test)

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

Checks if all elements in a list are equal.

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

Splits list into two groups. For every element in a list, if the corresponding boolean in another list is true, add the element to the first group; otherwise, add it to the second group. 

```
For example:
bifurcate(['beep', 'boop', 'foo', 'bar'], [true, true, false, true]) // [ ['beep', 'boop', 'bar'], ['foo'] ]
```

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

Chunks a list into smaller lists of a specified size. The last list in the resulting list may have less elements than the given size.

```
For example:
chunk([1, 2, 3, 4, 5], 2) // [[1,2],[3,4],[5]]
```

```kotlin
fun <T> chunk(list: List<T>, size: Int): List<List<T>> =
    list.chunked(size)
```

### compact

Removes "falsey" values from a list.

Kotlin doesn't distinguish falsey values but they are (`false`, `null`, `0`, `""`, `[]`, and `NaN`).

```kotlin
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
```

### countBy

Groups the elements of a list based on the given function and returns the count of elements in each group.

```
For example:
countBy([6.1, 4.2, 6.3], Math.floor) // {4: 1, 6: 2}
countBy(['one', 'two', 'three'], 'length') // {3: 2, 5: 1}
```

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

### crossProduct

Creates a cross product: forming a pair from each value in the first list to each value in the second list. 

```
For example:
crossProduct([1, 2], ['a', 'b']); // [[1, 'a'], [1, 'b'], [2, 'a'], [2, 'b']]
```

```kotlin
fun <T, U> crossProduct(first: List<T>, second: List<U>): List<Pair<T, U>> =
    first.flatMap { a -> second.map { b -> a to b } }
```

### difference

Returns a list of elements contained in the first list that are not present in the second list. 

```
For example:
difference([1, 2, 3], [1, 2, 4]) // [3]
```

```kotlin
fun <T> difference(first: List<T>, second: List<T>): List<T> =
    (first subtract second).toList()
```

### differenceBy

Returns a list of elements contained in the first list that are not present in the second list, after applying the provided function to each list element of both.

```kotlin
fun <T, R> differenceBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> =
    with(second.toSet().map(function)) {
        first.filterNot { contains(function(it)) }
    }
```

### differenceWith

Filters out all elements from the first list for which the comparator function does not return `true` for that element *and* every element in the second list.

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

### endsWith

Checks whether the given list ends with the given sublist. 

```kotlin
fun <T> endsWith(list: List<T>, subList: List<T>): Boolean =
    list.takeLast(subList.size) == subList
```

### everyNth

Returns every nth element in a list.

```
For example:
everyNth([1, 2, 3, 4, 5, 6], 2) // [ 2, 4, 6 ]
```

```kotlin
fun <T> everyNth(list: List<T>, nth: Int): List<T> =
    list.windowed(nth, nth, partialWindows = false).map { it.last() }
```

### filterNonUnique

Filters out the non-unique values in a list.

```kotlin
fun <T> filterNonUnique(list: List<T>): List<T> =
    list.distinct()
```

### filterNonUniqueBy

Filters out the non-unique values in an list, after applying the given function. 

```
For example: 
filterNonUniqueBy([a, b, c], letter => 1) // [a]
```

```kotlin
fun <T, K> filterNonUniqueBy(list: List<T>, function: (T) -> K): List<T> =
    list.distinctBy(function)
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

Groups the elements of a list based on the given function.

```
For example:
groupBy([6.1, 4.2, 6.3], Math.floor); // {4: [4.2], 6: [6.1, 6.3]}
groupBy(['one', 'two', 'three'], 'length'); // {3: ['one', 'two'], 5: ['three']}
```

```kotlin
fun <T, K> groupBy(list: List<T>, function: (T) -> K): Map<K, List<T>> =
    list.groupBy(function)
```

### hasDuplicates

Returns true if duplicate values exist in the list, false otherwise.

```kotlin
fun <T> hasDuplicates(list: List<T>): Boolean =
    list.toSet().size != list.size
```

### hasSubList

Checks whether the given list contains the given sublist. 

```kotlin
tailrec fun <T> hasSubList(list: List<T>, subList: List<T>): Boolean =
    when {
        subList.isEmpty() -> true
        list.isEmpty() -> subList.isEmpty()
        list.take(subList.size) == subList -> true
        else -> hasSubList(list.drop(1), subList)
    }
```

### head

Returns the head of a list.

```kotlin
fun <T> head(list: List<T>): T =
    list.first()
```
### indexOfAll

Returns all indices in a list where a given value is present.
If the value never occurs, returns an empty list. 

```
For example:
indexOfAll([1, 2, 3, 1, 2, 3], 1) // [0, 3]
indexOfAll([1, 2, 3], 4) // []
```

```kotlin
fun <T> indexOfAll(list: List<T>, target: T): List<Int> =
    list.withIndex().filter { it.value == target }.map { it.index }
```

### initial

Returns all the elements of an array except the last one.

```kotlin
fun <T> initial(list: List<T>): List<T> =
    list.dropLast(1)
```

### initialize2DList

Initializes a 2D list of given width and height and value.

```
For exmaple:
initialize2DList(2, 2, 0) // [[0, 0], [0, 0]]
```

```kotlin
fun <T> initialize2DList(width: Int, height: Int, value: T): List<List<T>> =
    List(height) { List(width) { value } }
```

### initializeListWithRange

Initializes a list containing the numbers in the specified range, where `start` and `stop` are inclusive with their common difference `step`.

```
For example:
initializeListWithRange(0, 9, 2); // [0, 2, 4, 6, 8]
```

```kotlin
fun initializeListWithRange(start: Int, stop: Int, step: Int): List<Int> =
    (start..stop step step).toList()
```

### initializeListWithValue

Initializes and fills a list with the specified value.

```
For example:
initializeListWithValue(5, 2); // [2, 2, 2, 2, 2]
```

```kotlin
fun <T> initializeListWithValue(size: Int, value: T): List<T> =
    List(size) { value }
```

### intersection

Returns a list of elements that exist in both lists.

```
For example:
intersection([1, 2, 3], [4, 3, 2]) // [2, 3]
```

```kotlin
fun <T> intersection(first: List<T>, second: List<T>): List<T> =
    (first intersect second).toList()
```

### intersectionBy

Returns a list of elements that exist in both lists, after applying the provided function to each element of both.

```
For example:
intersectionBy([2.1, 1.2], [2.3, 3.4], Math.floor) // [2.1]
```

```kotlin
fun <T, R> intersectionBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> =
    with(second.toSet().map(function)) {
        first.filter { contains(function(it)) }
    }
```

### intersectionWith

Returns a list of elements that exist in both lists, using a provided comparator function.

```
For example:
intersectionWith([1, 1.2, 1.5, 3, 0], [1.9, 3, 0, 3.9], (a, b) => Math.round(a) === Math.round(b)) // [1.5, 3, 0]
```

```kotlin
fun <T> intersectionWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    first.filter { a -> second.any { b -> function(a, b) } }
```

### join

Joins all elements of a list into a string.

```kotlin
fun <T> join(list: List<T>, separator: String = ", "): String =
    list.joinToString(separator)
```

### last

Returns the last element of a list. 

```kotlin
fun <T> last(list: List<T>): T =
    list.last()
```

### longest

Returns the longest collection in the list, or null if the list has no collections. 
If multiple collections have the same size, the first one will be returned.

```kotlin
fun <T> longest(list: List<Collection<T>>): Collection<T>? = 
    list.maxBy { it.size }
```

### mapObject

Maps the elements of a list to the result of applying a function to that element.
 
```
For example:
mapObject([1, 2, 3], a => a * a) // { 1: 1, 2: 4, 3: 9 }
```

```kotlin
fun <T, R> mapObject(list: List<T>, function: (T) -> R): Map<T, R> =
    list.associateWith(function)
```

### maxN

Returns the `n` maximum elements from the provided list.
If `n` is greater than or equal to the provided list's length, then return the original list (sorted in descending order).

```kotlin
fun <T : Comparable<T>> maxN(list: List<T>, n: Int): List<T> =
    list.sortedDescending().take(n)
```

### minN

Returns the `n` minimum elements from the provided list.
If `n` is greater than or equal to the provided list's length, then return the original list (sorted in ascending order).

```kotlin
fun <T : Comparable<T>> minN(list: List<T>, n: Int): List<T> =
    list.sorted().take(n)
```

### none

Returns `true` if the provided predicate function returns `false` for all elements in a list, `false` otherwise.

```
For example:
none([0, 1, 3, 0], x => x == 2) // true
none([-1, 1, 2], x => x <= 0); // false
```

```kotlin
fun <T> none(list: List<T>, predicate: (T) -> Boolean): Boolean =
    list.none(predicate)
```

### nthElement

Returns the nth element of a list.
If the index is out of bounds, throws `IndexOutOfBoundsException`.

```kotlin
fun <T> nthElement(list: List<T>, n: Int): T =
   list[n]
```

### offset

Returns a new list which moves the specified amount of elements to the end of the list.
If the offset is less than 0 or greater than the size of the list, then `IndexOutOfBoundsException` will be thrown. 
```
For example:
offset([1, 2, 3, 4, 5], 2); // [3, 4, 5, 1, 2]
offset([1, 2, 3, 4, 5], 5); // [1, 2, 3, 4, 5]
```

```kotlin
fun <T> offset(list: List<T>, offset: Int): List<T> =
    list.slice(offset until list.size) + list.slice(0 until offset)
```

### partition

Groups the elements into two lists, the first containing all elements for which the predicate evaluated to true, and the second containing all other elements.  

```kotlin
fun <T> partition(list: List<T>, predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    list.partition(predicate)
```

### product

Creates a cross product: applying the provided function to each value in the first list along with each value in the second list

```kotlin
fun <T, U, R> product(first: List<T>, second: List<U>, function: (T, U) -> R): List<R> =
    first.flatMap { t -> second.map { u -> function(t, u) } }
```

### pull

Filters out the elements specified.

```
For example:
let list = ['a', 'b', 'c', 'a', 'b', 'c']
pull(list, 'a', 'c') // [ 'b', 'b' ]
```

```kotlin
fun <T> pull(list: List<T>, vararg elements: T): List<T> =
    with(elements.toSet()) {
       list.filterNot { contains(it) }
    }
```

### pullAtIndex 

Filters out the indices specified.

```
For example:
let list = ['a', 'b', 'c', 'd']
let pulled = pullAtIndex(list, [1, 3]) // [ 'a', 'c' ] 
```

```kotlin
fun <T> pullAtIndex(list: List<T>, vararg indices: Int): List<T> =
    with(indices.toSet()) {
        list.filterIndexed { index, _ -> !contains(index) }
    }
```

### pullAtValue

Filters out the elements specified and returns them.

```
For example:
let list = ['a', 'b', 'c', 'd'];
pullAtValue(list, ['b', 'd', 'e']) // [ 'b', 'd' ]
```

```kotlin
fun <T> pullAtValue(list: List<T>, vararg elements: T): List<T> =
    with(elements.toSet()) {
        list.filter { contains(it) }
    }
```


### reduceSuccessive

This function is also commonly known as `scan`.
Applies a function against an accumulator and each element in the list (from left to right), returning a new list of successively calculated values.

```
For example:
reduceSuccessive([1, 2, 3, 4, 5, 6], 0, (acc, val) => acc + val) // [1, 3, 6, 10, 15, 21]
```

```kotlin
fun <T, R> reduceSuccessive(list: List<T>, identity: R, function: (R, T) -> R): List<R> {
    fun <T> List<T>.lastOrElse(t: T): T = lastOrNull() ?: t
    return list.fold(emptyList()) { acc, t -> acc + function(acc.lastOrElse(identity), t) }
}
```


### reject

Return a new list which removes elements from the list for which the given predicate returns `true`.

```
For example:
reject([1, 2, 3, 4, 5], x => x % 2 === 0) // [1, 3, 5]
reject(['Apple', 'Pear', 'Kiwi', 'Banana'], word => word.length > 4); // ['Pear', 'Kiwi']
```

```kotlin
fun <T> reject(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.filterNot(predicate)
```

### remove

Returns a new list which removes elements from the list for which the given predicate returns `false`.

```
For example:
remove([1, 2, 3, 4], n => n % 2 === 0) // [2, 4]
```

```kotlin
fun <T> remove(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.filter(predicate)
```

### sample

Returns a random element from a list.

```kotlin
fun <T> sample(list: List<T>): T =
    list.random()
```

### sampleSize

Gets `n` random elements from a list, up to the size of the list.

```kotlin
fun <T> sampleSize(list: List<T>, n: Int): List<T> =
    list.shuffled().take(n)
```

### shank

Returns a new list with changes in the contents of this list, removing or replacing existing elements and/or adding new elements.

```
For example:
const names = ['alpha', 'bravo', 'charlie'];
const namesAndDelta = shank(names, 1, 0, 'delta'); // [ 'alpha', 'delta', 'bravo', 'charlie' ]
const namesNoBravo = shank(names, 1, 1); // [ 'alpha', 'charlie' ]
```

`start` - Index at which to start changing the list
`deleteCount` - the number of list elements to remove, beginning from `start`
`elements` - the elements to add to the list, beginning from `start`


```kotlin
fun <T> shank(list: List<T>, start: Int = 0, deleteCount: Int = 0, vararg elements: T): List<T> =
    list.slice(0 until start) + elements + list.drop(start + deleteCount)
```

### shuffle

Returns a new list with the elements of this list randomly shuffled.

```kotlin
fun <T> shuffle(list: List<T>): List<T> =
    list.shuffled()
```

### sortOrder

Returns `1` if the list is sorted in ascending order, `-1` if it is sorted in descending order or `0` if it is not sorted.
A list with all equal values is considered sorted ascending. 

```
For example:
isSorted([0, 1, 2, 2]); // 1
isSorted([4, 3, 2]); // -1
isSorted([4, 3, 5]); // 0
```

```kotlin
fun <T : Comparable<T>> sortOrder(list: List<T>): Int =
    with(list.sorted()) {
        when {
            this == list ->  1
            this.asReversed() == list -> -1
            else -> 0
        }
    }
```

### startsWith

Checks whether the given list starts with the given sublist. 

```kotlin
fun <T> startsWith(list: List<T>, subList: List<T>): Boolean =
    list.take(subList.size) == subList
```

### symmetricDifference

Returns the symmetric difference between two lists, without filtering out duplicate values.

```
For example:
symmetricDifference([1, 2, 3], [1, 2, 4]); // [3, 4]
symmetricDifference([1, 2, 2], [1, 3, 1]); // [2, 2, 3]
```

```kotlin
fun <T> symmetricDifference(first: List<T>, second: List<T>): List<T> =
    ((first subtract second) + (second subtract first)).toList()
```

### symmetricDifferenceBy

Returns the symmetric difference between two lists, after applying the provided function to each element of both.

```
For example:
symmetricDifferenceBy([2.1, 1.2], [2.3, 3.4], Math.floor) // [1.2, 3.4]
```

```kotlin 
fun <T, R> symmetricDifferenceBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> {
    val mapFirst = first.toSet().map(function)
    val mapSecond = second.toSet().map(function)
    return first.filterNot { mapSecond.contains(function(it)) } + second.filterNot { mapFirst.contains(function(it)) }
}
```

### symmetricDifferenceWith

Returns the symmetric difference between two lists, using a provided function as a comparator.

```
For example:
symmetricDifferenceWith(
  [1, 1.2, 1.5, 3, 0],
  [1.9, 3, 0, 3.9],
  (a, b) => Math.round(a) === Math.round(b)
) // [1, 1.2, 1.9, 3.9]
```

```kotlin
fun <T> symmetricDifferenceWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    first.filter { a -> second.none { b -> function(a ,b) } } +
        second.filter { b -> first.none { a -> function(a, b) } }
```

### tail

Returns all elements in a list except for the first one.

```kotlin
fun <T> tail(list: List<T>): List<T> =
    list.drop(1)
```

### take

Returns the first `n` elements. 

Use `Array.prototype.slice()` to create a slice of the array with `n` elements taken from the beginning.

```kotlin
fun <T> take(list: List<T>, n: Int): List<T> =
    list.take(n)
```

### takeRight

Returns the last `n` elements. 

```kotlin
fun <T> takeRight(list: List<T>, n: Int): List<T> =
    list.takeLast(n)
```

### takeRightWhile

Returns the last `n` elements satisfying the given predicate. 

```
For example:
takeRightWhile([1, 2, 3, 4], n => n >= 3) // [3, 4]
```

```kotlin
fun <T> takeRightWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.takeLastWhile(predicate)
```

### takeWhile

Returns the first `n` elements satisfying the given predicate. 

```
For example:
takeWhile([1, 2, 3, 4], n => n < 3) // [1, 2]
```

```kotlin
fun <T> takeWhile(list: List<T>, predicate: (T) -> Boolean): List<T> =
    list.takeWhile(predicate)
```

### union

Returns every element that exists in any of the two lists, removing duplicates. 

```
For example:
union([1, 2, 3], [4, 3, 2]) // [1, 2, 3, 4]
```

```kotlin
fun <T> union(first: List<T>, second: List<T>): List<T> =
    (first union second).toList()
```

### unionBy

Returns every element that exists in any of the two lists once, after applying the provided function to each element of both.

```
For example:
unionBy([2.1], [1.2, 2.3], Math.floor) // [2.1, 1.2]
```

```kotlin
fun <T, R> unionBy(first: List<T>, second: List<T>, function: (T) -> R): List<T> {
    val mapFirst = first.toSet().map(function)
    return (first.toSet() + second.toSet().filterNot { mapFirst.contains(function(it)) }).toList()
}
```

### unionWith

Returns every element that exists in any of the two lists once, using a provided comparator function.

```
For example:
unionWith([1, 1.2, 1.5, 3, 0], [1.9, 3, 0, 3.9], (a, b) => Math.round(a) === Math.round(b)) // [1.5, 3, 0, 3.9]
```

```kotlin
fun <T> unionWith(first: List<T>, second: List<T>, function: (T, T) -> Boolean): List<T> =
    (first.filter { a -> second.any { b -> function(a, b) } } union
            second.filter { b -> first.any { a -> function(a, b) } }).toList()
```

### unzip

Transforms a list of pairs to a pair of lists. 

```kotlin
fun <T, U> unzip(list: List<Pair<T, U>>): Pair<List<T>, List<U>> =
    list.unzip()
```

### zip

Returns a list of pairs built from the elements of each list with the same index. The returned list has length of the shortest list, so the longer list has some ignored elements.

```kotlin
fun <T, U> zip(first: List<T>, second: List<U>): List<Pair<T, U>> =
    first.zip(second)
```

### zipAll

Returns a list of pairs built from the elements of each list with the same index, using the default value if any list is shorter.
The returned list has length of the longest list

```
For example:
zipAll([1, 2, 3], 0, [a, b, c, d, e], z) // [[1, a], [2, b], [3, c], [0, d], [0, e]]
```

```kotlin
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
```

### zipKeysValues

Zip a list of keys and a list of values into a Map. Since a Map cannot have duplicate keys, if the list of keys has any duplicates,
only the last one will be kept. 

```kotlin
fun <K, V> zipKeysValues(keys: List<K>, values: List<V>): Map<K, V> =
    keys.zip(values).toMap()
```

### zipWith 

Returns a list formed by applying the given function to elements of matching indices in both lists. The returned list has length of the shortest list, so the longer list has some ignored elements.

```kotlin
fun <T, U, R> zipWith(first: List<T>, second: List<U>, function: (T, U) -> R): List<R> =
    first.zip(second).map { (t, u) -> function(t, u) }
```

### zipWithIndex 

Returns a list of pairs built from the elements of the list along with its index.

```kotlin
fun <T> zipWithIndex(list: List<T>): List<Pair<Int, T>> =
    list.withIndex().map { it.index to it.value }
```

### zipWithNext 

Returns a list of pairs built from the elements of the list along with the element at the next index.

```kotlin
fun <T> zipWithNext(list: List<T>): List<Pair<T, T>> =
    list.zipWithNext()
```

---

## Function

### allOf

Given a list of predicates, returns a single predicate that evaluates to true if all of the predicates evaluate to true, and false otherwise. 

```kotlin
fun <T> allOf(vararg predicates: (T) -> Boolean): (T) -> Boolean =
    { t -> predicates.all { it(t) } }
```

### andThen

Returns a function which first applies `this` function to the input, and then applies the `after` function to the result. 

```kotlin
String::decapitalize andThen String::hashCode
```

```kotlin
infix fun <T, U, R> ((T) -> U).andThen(after: (U) -> R): (T) -> R = { after(this(it)) }
```

### anyOf

Given a list of predicates, returns a single predicate that evaluates to true if any of the predicates evaluate to true, and false otherwise. 

```kotlin
fun <T> anyOf(vararg predicates: (T) -> Boolean): (T) -> Boolean =
    { t -> predicates.any { it(t) } }
```

### applyFirst

Applies the first argument of a curried function, returning a function taking 1 less argument. 

```kotlin
fun <T, U, R> applyFirst(function: (T) -> (U) -> R, first: T): (U) -> R = function(first)
```

### applySecond

Applies the second argument of a curried function, returning a function taking 1 less argument. 

```kotlin
fun <T, U, R> applySecond(function: (T) -> (U) -> R, second: U): (T) -> R = { t -> function(t)(second) }
```

### compose

Returns a function which first applies the `before` function to the input, and then applies `this` function to the result. 

```kotlin
String::hashCode compose String::decapitalize
```

```kotlin
infix fun <T, U, V> ((U) -> V).compose(before: (T) -> U): (T) -> V = { this(before(it)) }
```

### constant

Returns a function which always evaluates to the same result, no matter the input. 

```kotlin
fun <T, R> constant(result: R): (T) -> R = { result }
```

### curry 

Transforms a function that takes multiple arguments into one that takes a single argument - and returns another function also taking a single argument. 

```kotlin
fun <T, U, R> ((T, U) -> R).curry(): (T) -> (U) -> R = { t -> { u -> this(t, u) } }
```

### diverge

Tests a value against a predicate and executes either the success function or failure function. 

```kotlin
fun <T, R> diverge(t: T, predicate: (T) -> Boolean, onSuccess: (T) -> R, onFailure: (T) -> R): R =
    if (predicate(t)) onSuccess(t) else onFailure(t)
```

### identity

Returns a function that always returns its input argument. 

```kotlin
fun <T> identity(): (T) -> T = { it }
```

### lift

Takes a function operating on raw values and lifts it to a function operating on `Result` values. 

```kotlin
fun <T, U, R> lift(function: (T) -> (U) -> R): (Result<T>) -> (Result<U>) -> Result<R> =
    { resultT -> { resultU -> resultT.mapCatching(function).mapCatching { resultU.map(it) }.mapCatching { it.getOrThrow() } } }
```

### memoize

Returns a memoized version of the given function - the function now caches all of its results. 

```kotlin
fun <T, R> memoize(function: (T) -> R): (T) -> R =
    with(ConcurrentHashMap<T, R>()) {
        { t -> computeIfAbsent(t) { function(it) } }
    }
```

### noneOf

Given a list of predicates, returns a single predicate that evaluates to true if none of the predicates evaluate to true, and false otherwise. 

```kotlin
fun <T> noneOf(vararg predicates: (T) -> Boolean): (T) -> Boolean =
    { t -> !predicates.any { it(t) } }
```

### retry

Returns a retrying version of the given function. 

> This implementation is based on Pierre-Yves Saumont's implementation in [The Joy of Kotlin](https://www.manning.com/books/the-joy-of-kotlin)

```kotlin
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
```

### sequence

Reduces many functions into a single function which produces a list. 

```kotlin
fun <T, R> sequence(list: List<(T) -> R>): (T) -> List<R> = { t -> list.map { it(t) } }
```

### swapArgs

Swaps the arguments of a curried function. 

```kotlin
fun <T, U, R> ((T) -> (U) -> R).swapArgs(): (U) -> (T) -> R = { u -> { t -> this(t)(u) } }
```

### time

Times a function and returns the time as well as the result. 

```kotlin
fun <R> time(timeUnit: TimeUnit = TimeUnit.NANOSECONDS, function: () -> R): Pair<Long, Result<R>> {
    val start = System.nanoTime()
    val result = runCatching(function)
    val time = System.nanoTime() - start
    return timeUnit.convert(time, TimeUnit.NANOSECONDS) to result
}
```

### uncurry

Transforms a series of single argument functions into a single function with multiple arguments. 

```kotlin
fun <T, U, R> ((T) -> (U) -> R).uncurry(): (T, U) -> R = { t, u -> this(t)(u) }
```

### unlift

Takes a function operating on `Result` values and unlifts it to one operating on raw values. 

```kotlin
fun <T, U, R> unlift(function: (Result<T>) -> (Result<U>) -> Result<R>): (T) -> (U) -> R =
    { t -> { u -> function(Result.success(t))(Result.success(u)).getOrThrow() } }
```

---

## Lazy

### asSequence

Transforms a lazy value into a lazy list, i.e. a Sequence. 

```kotlin
fun <T> Lazy<T>.asSequence(): Sequence<T> = object : Iterator<T> {
        override fun hasNext(): Boolean = true
        override fun next(): T = value
    }.asSequence()
```

### filter

Evaluates a predicate against the value produced by the Lazy and returns a successful Result if the predicate evaluates to true, or a failure Result otherwise. 

```kotlin
fun <T> Lazy<T>.filter(predicate: (T) -> Boolean): Lazy<Result<T>> =
    lazy { if (predicate(value)) Result.success(value) else Result.failure(IllegalArgumentException("Predicate evaluated to false.")) }
```

### flatMap

Applies a function that produces a Lazy to the value produced by `this` Lazy. 

```kotlin
fun <T, R> Lazy<T>.flatMap(function: (T) -> Lazy<R>): Lazy<R> =
    lazy { function(value).value }

```

### getOrDefault

Safely gets the value produced by `this` Lazy and optionally returns the default value if any exception is thrown by the Lazy. 
The initialization of the value produced by a Lazy may throw an exception, this method guarantees that no exceptions are thrown while initializing that value. 

```kotlin
fun <R, T : R> Lazy<T>.getOrDefault(default: R): R =
    runCatching { value }.getOrDefault(default)
```

### lift

Takes a function operating on raw values and lifts it to a function operating on Lazy values.

```kotlin
fun <T, U, R> lift(function: (T) -> (U) -> R): (Lazy<T>) -> (Lazy<U>) -> Lazy<R> =
    { lazyT -> { lazyU -> lazy { function(lazyT.value)(lazyU.value) } } }
```

### map

Applies a function to the value produced by `this` Lazy. 

```kotlin
fun <T, R> Lazy<T>.map(function: (T) -> R): Lazy<R> =
    lazy { function(value) }
```

### map2

Applies a function taking 2 raw values to 2 values produced by Lazys, and returns another Lazy. 

```kotlin
fun <T, U, R> map2(lazy1: Lazy<T>, lazy2: Lazy<U>, function: (T) -> (U) -> R): Lazy<R> =
    lazy { function(lazy1.value)(lazy2.value) }
```

### sequence

Reduces many Lazys into a single Lazy which produces a list.

```kotlin
fun <T> sequence(list: List<Lazy<T>>): Lazy<List<T>> =
    lazy { list.map { it.value } }
```

### sequenceCatching

Reduces many Lazys into a single Lazy which produces a Result of type list. If any of the passed in Lazys throw an exception the Result will be a failure. 

```kotlin
fun <T> sequenceCatching(list: List<Lazy<T>>): Lazy<Result<List<T>>> =
    lazy { runCatching { list.map { it.value } } }
```

### test

Lazily tests the value produced by `this` Lazy against a predicate and returns a Lazy boolean. 

```kotlin
fun <T> Lazy<T>.test(predicate: (T) -> Boolean): Lazy<Boolean> =
    lazy { predicate(value) }
```

---

## Map

### concat

Concatenates multiple maps into a single map, preserving the order of the passed in entries.

> Note there is expensive list concatenation in this snippet. 

```kotlin
fun <K, V> concat(first: Map<K, V>, vararg others: Map<K, V>): Map<K, List<V>> =
    first.mapValues { entry -> listOf(entry.value) }.toMap(LinkedHashMap()).apply {
        others.forEach {
            map -> map.forEach { key, value ->  merge(key, listOf(value)) { list1, list2 -> list1 + list2 } }
        }
    }
```

### partition

Splits the original map into a pair of maps, where the first map has all entries for which the predicate evaluated to `true`, and the second contains all other entries.

```kotlin
fun <K, V> Map<K, V>.partition(predicate: (K) -> Boolean): Pair<Map<K, V>, Map<K, V>> =
    (HashMap<K, V>() to HashMap<K, V>()).apply {
        forEach { key, value -> if (predicate(key)) first.put(key, value) else second.put(key, value) }
    }
```

### pick

Picks the map entries which have keys contained in the given list. 

```kotlin
fun <K, V> Map<K, V>.pick(list: List<K>): Map<K, V> =
    list.toSet().run {
        filterKeys { contains(it) }
    }
```

### toEnumMap

Given a function, transforms all the values in an enum class into an `EnumMap`, where the key is the enum value and the value is the result of applying the function to the enum value. 

````kotlin
enum class Stooge { MOE, LARRY, CURLY }

fun main() {
    Stooge::class.toEnumMap { it.toString().length }.forEach(::println)
}
````

```kotlin
inline fun <reified K : Enum<K>, V> KClass<K>.toEnumMap(function: (K) -> V): EnumMap<K, V> =
    enumValues<K>().fold(EnumMap(this.java)) { map, key -> map.apply { put(key, function(key)) } }
```

---

#### Related projects

* [30 Seconds of Code](https://github.com/30-seconds/30-seconds-of-code)
* [30 Seconds of Java](https://github.com/shekhargulati/30-seconds-of-java)
* [30 Seconds of React](https://github.com/30-seconds/30-seconds-of-react)
* [30 Seconds of Python](https://github.com/kriadmin/30-seconds-of-python-code)
* [30 Seconds of PHP](https://github.com/appzcoder/30-seconds-of-php-code)

## License

MIT