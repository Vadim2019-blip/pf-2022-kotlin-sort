import java.io.File

/*
-------------------------------------------------------------------
Утилита сорт.
0) Пограмма поддерживает вывод как в меню Run, если нет флага -o, так и изменение внутри файла
1) Как вводить данные: Нужно написать название файла, затем несколько флагов, затем название следующего и тд.
В конце ОБЯЗАТЕЛЬНО нужно ввести endinput
1.1) Поддерживается формат, когда к файлу можно применить несколько флагов, однако лучше к файлу применять ровно один флаг а затем писать -o
2)
Поддерживаемые флаги:
-f, -n, -o, -r, -random
2.1) Флаг -max не поддерживает функцию изменения файла, тк это бессмысленно, ведь соддержание файла пропадет

3) Примеры ввода данных:
input -f file -f -o -r -u number -n endinput
input -f -r file -f number -n endinput
4) Файлы должны быть созданы или лежать в папке с проектом, исходно там лежат input, file и number
5) флаг -random можно использовать только без других флагов
6) Названия файлов не могут начинаться на -
7) Флаг -random простой, но очень полезный, благодаря нему можно не вбивать данные в файл после сортировки
-------------------------------------------------------------------
*/

/*Функция которая проверяет является ли строка числом нужна для реализации флага -n */
fun isNumber(a: String): Boolean {
    var flag = true
    for(i in 0..a.length - 1){
        if(!a[i].isDigit()){
            flag = false
            break
        }
    }
    return flag
}

/* Функция для проверки существования Файла в системе, что бы программа не падала*/
fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}

fun main(args: Array<String>) {

    var input = mutableListOf<String>()
    val ListFlag = arrayOf("-f", "-r", "-u", "-V", "-o")

    var ListOfIndexFiles = mutableListOf(0)
    var count = 0
    /*создаем переменные, которые отвечают за наличие флашов*/
    var flag = false
    var flag_o = false
    var flag_r = false
    var flag_n  = false
    var flag_max = false
    var flag_random = false

    /*Список со всеми индексами, потом мы будем удалять из него те, которые не являются названием файла*/
    for(i in 0..args.size -1){
        ListOfIndexFiles.add(i)
    }
    /* обратавыем флаги и названия файлов формируем список с индексами названий файлов*/
    for (arg in args) {
        if (arg[0] == '-') {
            ListOfIndexFiles.remove(count)
        }
        count += 1
    }
    /* начинаем работу основную работу: применение к файлам флагов*/
    for(i in 0..ListOfIndexFiles.size - 2){
        /* проверяем существование данного файла*/
        val file = File(args[ListOfIndexFiles[i]])
        if(!isFileExists(file)){
            println("$file такого файла нету")
        }
        else {
            File(args[ListOfIndexFiles[i]]).useLines { lines -> lines.forEach { input.add(it) } }
        }
        /* получаем список флагов, применяющихся к данному файлу */
        for(type in args.slice(ListOfIndexFiles[i] + 1..ListOfIndexFiles[i+1] - 1)){
            if(type == "-f"){
                flag = true
            }
            else if(type == "-r"){
                flag_r = true
            }
            else if(type == "-u"){
               val c = input.toMutableSet()
                input = c.toMutableList()
            }
            else if(type == "-o"){
                flag_o = true
            }
            else if(type == "-n"){
                flag_n = true
            }
            else if(type == "-random"){
                flag_random = true
            }
            else if(type == "-max"){
                flag_max = true
            }
            else{
                println("$type программа не поддерживает такой флаг")
            }
        }
        /*Далее идет действие применения флагов к файлам*/
        if(flag){
            input.sortWith(String.CASE_INSENSITIVE_ORDER)
        }
        else input.sort()
        if (flag_max){
            println(input[0])
        }
        if(flag_n){
            var inputcopy = mutableListOf<Int>()
            var inputcopy2 = mutableListOf<String>()
            for(str in input){
                if(isNumber(str)){
                    var strt = str.toInt()
                    inputcopy.add(strt)
                }
                else{
                    inputcopy2.add(str)
                }
            }
            var c = args[ListOfIndexFiles[i]]
            if(inputcopy.size == 0){
                println("В файле $c нет цифр, поэтому флаг -n пременить нельзя")
            }
            else {
                input.clear()
                inputcopy.sort()
                inputcopy2.sort()
                for (i in 0..inputcopy.size - 1) {
                    input.add(inputcopy[i].toString())
                }
                for (i in inputcopy.size..inputcopy.size + inputcopy2.size - 1) {
                    input.add(inputcopy2[i - inputcopy.size])
                }
            }
        }
        if (flag_r) {
            input = input.asReversed()
            }
        if(flag_random){
            input.shuffle()
        }
        if(flag_o) {
            var count1 = 0
            for(str in input) {
                if (count1 == 0) {
                    File(args[ListOfIndexFiles[i]]).writeText((str).toString(), Charsets.UTF_8)
                }
                else {
                    File(args[ListOfIndexFiles[i]]).appendText(("\n" + str).toString(), Charsets.UTF_8)
                }
                count1 += 1
            }
        }
        else {
            println(input)
        }
        /*Последняя часть input очищаетс и переменные, отвечающие за наличие флагов стоновятся равны false*/
        input.clear()
        flag = false
        flag_o = false
        flag_r = false
        flag_n = false
    }
}