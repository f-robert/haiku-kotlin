import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HaikuTest {

    companion object {

        private const val distinctLetters = "breakingthoupvmwcdflsy"

        private val wordlePattern = "[\\s,.!?-]+".toPattern()

        private val haiku = """
            Breaking Through                  Pavement                  Wakin' with Bacon        Homeward Found
            ----------------                  --------                  -----------------        --------------
            The wall disappears               Beautiful pavement!       Wakin' with Bacon        House is where I am
            As soon as you break through the  Imperfect path before me  On a Saturday morning    Home is where I want to be
            Intimidation                      Thank you for the ride    Life’s little pleasures  Both may be the same
                        
            Winter Slip and Slide              Simple Nothings                With Deepest Regrets
            ---------------------              ---------------                --------------------
            Run up the ladder                  A simple flower                With deepest regrets
            Swoosh down the slide in the snow  Petals shine vibrant and pure  That which you have yet to write
            Winter slip and slide              Stares into the void           At death, won't be wrote
                        
            Caffeinated Coding Rituals  Finding Solace               Curious Cat                Eleven
            --------------------------  --------------               -----------                ------
            I arrange my desk,          Floating marshmallows        I see something move       This is how many
            refactor some ugly code,    Cocoa brewed hot underneath  What it is, I am not sure  Haiku I write before I
            and drink my coffee.        Comfort in a cup             Should I pounce or not?    Write a new tech blog.
            """.trimIndent()

    }

    @Test
    fun `Top letters`() {
        val chars = haiku
            .filter(Char::isLetter)
            .map(Char::lowercaseChar)
            .groupingBy { it }
            .eachCount()

        val top3 = chars.top(3)

        assertEquals(Pair('e', 94), top3[0])
        assertEquals(Pair('t', 65), top3[1])
        assertEquals(Pair('i', 62), top3[2])
    }

    @Test
    fun `Distinct letters`() {
        val actualLetters = haiku
            .filter(Char::isLetter)
            .map(Char::lowercaseChar)
            .distinct()
            .joinToString(separator = "")

        assertEquals(distinctLetters, actualLetters)
    }

    @Test
    fun `Duplicates and unique`() {
        val chars = haiku
            .filter(Char::isLetter)
            .map(Char::lowercaseChar)
            .groupingBy { it }
            .eachCount()

        val duplicates = chars.filter { it.value > 1}
        val unique = chars.filter { it.value == 1 }

        assertEquals(chars, duplicates)
        assertTrue(unique.isEmpty())
    }

    @Test
    fun `Top vowel and consonant`() {
        val charIntPairs = haiku
            .filter(Char::isLetter)
            .map(Char::lowercaseChar)
            .groupingBy { it }
            .eachCount()
            .top(26)

        val topVowel = charIntPairs
            .filter { it.first.isVowel() }
            .maxByOrNull { it.second }
            ?.first

        val topConsonant = charIntPairs
            .filterNot { it.first.isVowel() }
            .maxByOrNull { it.second }
            ?.first

        assertEquals('e', topVowel)
        assertEquals('t', topConsonant)
    }

    @Test
    fun `Haiku wordle words`() {
        val words = wordlePattern.split(haiku)

        assertEquals(168, words.size)

        val wordleWords = words
            .filterNot { "'" in it }
            .filter { it.length == 5 }
            .map(String::lowercase)
            .toSet()

        val expected = setOf(
            "haiku", "death", "wrote", "bacon", "shine", "house", "where", "thank",
            "break", "which", "cocoa", "drink", "write", "slide", "found")

        assertEquals(expected, wordleWords)
    }

}
