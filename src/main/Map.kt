import java.util.EnumMap
import kotlin.reflect.KClass

fun <K, V> concat(first: Map<K, V>, vararg others: Map<K, V>): Map<K, List<V>> =
    first.mapValues { entry -> listOf(entry.value) }.toMap(LinkedHashMap()).apply {
        others.forEach {
            map -> map.forEach { key, value ->  merge(key, listOf(value)) { list1, list2 -> list1 + list2 } }
        }
    }

fun <K, V> Map<K, V>.partition(predicate: (K) -> Boolean): Pair<Map<K, V>, Map<K, V>> =
    (HashMap<K, V>() to HashMap<K, V>()).apply {
        forEach { key, value -> if (predicate(key)) first.put(key, value) else second.put(key, value) }
    }

fun <K, V> Map<K, V>.pick(list: List<K>): Map<K, V> =
    list.toSet().run {
        filterKeys { contains(it) }
    }

inline fun <reified K : Enum<K>, V> KClass<K>.toEnumMap(function: (K) -> V): EnumMap<K, V> =
    enumValues<K>().fold(EnumMap(this.java)) { map, key -> map.apply { put(key, function(key)) } }
