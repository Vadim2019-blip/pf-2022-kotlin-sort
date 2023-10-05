import kotlin.test.*

internal class Test1 {
    /*Тесты парсинга аргументов и вспомогательных функций*/
    @Test
    fun test5(){
        assertEquals(true, isNumber("-123"))
        assertEquals(false, isNumber("-1asd23"))
        assertEquals(true, isNumber("1111"))
        assertEquals(false, isNumber("-123a123a"))


    }
    @Test
    fun test6(){
        assertEquals(true, isversions("1.1.1.1"))
        assertEquals(true, isversions("1123.123.123"))
        assertEquals(false, isversions("1.1.1.aaa"))
        assertEquals(false, isversions("1.1.asd1.asd1"))

    }
    /*Тесты самих сортировок*/
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
    @Test
    fun test7() { // проверяем WhitwsPACE
        var file = mutableListOf("123 121", "b", "c")
        val expected = mutableListOf("b", "123", "c", "121")
        assertEquals(expected, flag_wsSort(file))
    }
    /*Комбинированые тесты флагов*/
    @Test
    fun test8() { // проверяем REVERSE SORT и Version
        var file = mutableListOf("1.2.3", "1.0.0", "c")
        val expected = mutableListOf("1.0.0", "1.2.3", "c")
        assertEquals(expected, flag_VSort(flag_rSort(file)))
    }
    @Test
    fun test9() { // проверяем REVERSE SORT и WhitwsPACE и Version
        var file = mutableListOf("1.2.3 asd", "1.0.0", "c")
        val expected = mutableListOf("1.0.0", "1.2.3", "asd", "c")
        assertEquals(expected, flag_VSort(flag_rSort(flag_wsSort(file))))
    }
    @Test
    fun test10() { // проверяем REVERSE SORT и WhitwsPACE и Version
        var file = mutableListOf("1.2.3 asd", "1.0.0", "c")
        val expected = mutableListOf("1.0.0", "1.2.3", "asd", "c")
        assertEquals(expected, flag_VSort(flag_rSort(flag_wsSort(file))))
    }
    @Test
    fun test11() { // проверяем Ignore_CAse
        var file = mutableListOf("aaa", "AAA", "asdasd")
        val expected = mutableListOf("aaa", "AAA", "asdasd")
        assertEquals(expected, (IgnorCase(file)))
    }
}