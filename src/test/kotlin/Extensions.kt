private const val vowels = "aeiouAEIOU"

/**
 * Returns whether this [Char] is a vowel.
 */
fun Char.isVowel() = this in vowels

/**
 * Returns the top [n]th occurrences in this [Map].
 */
fun <K, V : Comparable<V>> Map<K, V>.top(
    n: Int
) = map { it.value to it.key }
    .groupBy({ it.first }, { it.second })
    .toSortedMap(compareBy<V> { it }.reversed())
    .entries
    .flatMap { it.value.map { e -> e to it.key } }
    .take(n)
