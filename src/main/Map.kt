import java.util.EnumMap
import kotlin.reflect.KClass

fun <K, V> merge(first: Map<K, V>, vararg others: Map<K, V>): Map<K, List<V>> =
    first.mapValues { entry -> listOf(entry.value) }.toMap(LinkedHashMap()).apply {
        others.forEach {
                map -> map.forEach { key, value ->  merge(key, listOf(value)) { list1, list2 -> list1 + list2 } }
        }
    }

fun <K, V> Map<K, V>.pick(list: List<K>): Map<K, V> =
    list.toSet().run {
        filterKeys { contains(it) }
    }

fun <K, V> Map<K, V>.split(predicate: (K) -> Boolean): Pair<Map<K, V>, Map<K, V>> =
    (HashMap<K, V>() to HashMap<K, V>()).apply {
        forEach { key, value -> if (predicate(key)) first.put(key, value) else second.put(key, value) }
    }

inline fun <reified K : Enum<K>, V> KClass<K>.toEnumMap(function: (K) -> V): EnumMap<K, V> =
    enumValues<K>().fold(EnumMap(this.java)) { map, key -> map.apply { put(key, function(key)) } }
