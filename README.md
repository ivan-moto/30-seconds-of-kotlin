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
fun <T, U> differenceBy(first: List<T>, second: List<T>, function: (T) -> U): List<T> =
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
fun <T, U> intersectionBy(first: List<T>, second: List<T>, function: (T) -> U): List<T> =
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
fun <T, U> mapObject(list: List<T>, function: (T) -> U): Map<T, U> =
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
fun <T, U, V> product(first: List<T>, second: List<U>, function: (T, U) -> V): List<V> =
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
fun <T, U> reduceSuccessive(list: List<T>, identity: U, function: (U, T) -> U): List<U> {
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
fun <T, U> symmetricDifferenceBy(first: List<T>, second: List<T>, function: (T) -> U): List<T> {
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
fun <T, U> unionBy(first: List<T>, second: List<T>, function: (T) -> U): List<T> {
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
fun <T, U, V> zipWith(first: List<T>, second: List<U>, function: (T, U) -> V): List<V> =
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


## License

MIT