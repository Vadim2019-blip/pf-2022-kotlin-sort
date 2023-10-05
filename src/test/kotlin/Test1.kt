import kotlin.test.*

internal class Test1 {

    @Test
    fun test1() { // проверяем Version Sort
        var file = mutableListOf("1.0.0", "0.0.0", "1.1.1", "1.2.2")
        val expected = mutableListOf("0.0.0", "1.0.0", "1.1.1", "1.2.2")
        assertEquals(expected, flag_VSort(file))
    }

    @Test
    fun test2() { // проверяем Version Sort
        var file = mutableListOf("a", "b", "c")
        val expected = mutableListOf("c", "b", "a")
        assertEquals(expected, flag_rSort(file))
    }
    @Test
    fun test3() { // проверяем REVERSE SORT
        var file = mutableListOf("a", "b", "c")
        val expected = mutableListOf("c", "b", "a")
        assertEquals(expected, flag_rSort(file))
    }

    @Test
    fun test4() { // проверяем WhitwsPACE
        var file = mutableListOf("a b c d", "b", "c")
        val expected = mutableListOf("b", "a", "c", "d", "c", "b")
        assertEquals(expected, flag_wsSort(file))
    }
}