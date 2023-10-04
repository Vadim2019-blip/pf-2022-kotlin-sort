import java.io.File
import java.util.*
/*
-------------------------------------------------------------------
Утилита сорт.
0) Пограмма поддерживает вывод как в меню Run, если нет флага -o, так и изменение внутри файла
1) Как вводить данные: Нужно написать название файла, затем несколько флагов, затем название следующего и тд.
В конце ОБЯЗАТЕЛЬНО нужно ввести endinput
1.1) Поддерживается формат, когда к файлу можно применить несколько флагов, однако лучше к файлу применять ровно один флаг а затем писать -o
2)
Поддерживаемые флаги:
-f, -n, -o, -r, -random, -numberanalis, -V
2.1) Флаг -V поддерживает сортировку по трем точкам. Если точек больше, то ошибки тоже не будет, однако результат сортировки может быть не таким каким нужно
2.2) Флаг -numberanalis создан в качестве дополнение к флагу -n, он служит для вывода информации о числах в файле, для удобства ввыводит в окно Run, ведь если бы он записывал в файл, то потом было бы трудно применять другие флаги
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


/*Просто функция для удобства: поменять местами два элемента массива */
fun <T> swap(list: MutableList<T>, i: Int, j: Int) {
    val t = list[i]
    list[i] = list[j]
    list[j] = t
}


/*Функция проверяющая является ли строка версиией*/
fun isversions(string1: String): Boolean {
    var ans = true
    var pointindex = mutableListOf<Int>()
    pointindex.add(-1)
    for (j in 0..string1.length - 1) {
        if (string1[j] == '.') {
            pointindex.add(j)
        }
    }
    for (i in 1..pointindex.size - 1) {
        var curr1 = string1.substring(pointindex[i - 1] + 1, pointindex[i])
        if (!isNumber(curr1)) {
            ans = false
            break
        }
    }
    var currlast: String
    if(pointindex[pointindex.size - 1] != string1.length - 1){
        currlast = string1.substring(pointindex[pointindex.size - 1] + 1, string1.length)
    }
    else{
        currlast = "aaaa"
    }
    if(pointindex.size == 1){
        return false
    }
    else if(!isNumber(currlast)){
        return false
    }
    else{
        return ans
    }
}


/* Функция, которая по версии возвращает число, аналогично семинару про айпи: разделяется по точкам и начиная с конца умножается на 256 */
fun ParsVers(string1: String): Int{
    var indexpoint = mutableListOf<Int>()
    var ans = 0
    indexpoint.add(-1)
    for(i in 0 until string1.length){
        if(string1[i] == '.'){
            indexpoint.add(i)
        }
    }
    var count = 4096 * 4096
    for(i in 1 until indexpoint.size){
        ans += string1.substring(indexpoint[i - 1] + 1, indexpoint[i]).toInt() * count
        count /= 256
    }
    ans  += string1.substring(indexpoint[indexpoint.size - 1] + 1, string1.length).toInt()
    return ans
}



/* Функция для проверки существования Файла в системе, что бы программа не падала*/
fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}

fun main(args: Array<String>) {

    var input = mutableListOf<String>()

    var ListOfIndexFiles = mutableListOf(0)
    var count = 0
    /*создаем переменные, которые отвечают за наличие флашов*/
    var flag = false
    var flag_o = false
    var flag_r = false
    var flag_n  = false
    var flag_numberanalis = false
    var flag_random = false
    var flag_V = false

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
            else if(type == "-numberanalis"){
                flag_numberanalis = true
            }
            else if(type == "-V"){
                flag_V = true
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
                if (flag_numberanalis){
                    var sum = 0
                    var min = inputcopy[0]
                    var median: Int
                    if((inputcopy.size - 1) % 2 == 0){
                        median = inputcopy[(inputcopy.size - 1)/2]
                    }
                    else{
                        median =  inputcopy[(inputcopy.size)/2]
                    }
                    var max = inputcopy[inputcopy.size -1]
                    for(i in 0..inputcopy.size -1) {
                        sum += inputcopy[i]
                    }
                    var mean = sum/inputcopy.size
                    println("Максимум: $max , Минимум: $min, Медиана: $median, Сумма: $sum, Среднее: $mean")
                }
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
        if(flag_V){
            var versions = mutableListOf<String>()
            var notversion = mutableListOf<String>()
            for(i in 0..input.size - 1){
                if(isversions(input[i])){
                    versions.add(input[i])
                }
                else{
                    notversion.add(input[i])
                }
            }
            for(i in 0 until versions.size){
                for(j in i + 1 until versions.size){
                    if(ParsVers(versions[i]) < ParsVers(versions[j])){
                        swap(versions, i, j)
                    }
                }
            }
            input.clear()
            for(i in 0..versions.size - 1){
                input.add(versions[i])
            }
            for(i in 0..notversion.size - 1){
                input.add(notversion[i])
            }
        }
        if(flag_o) {
            var count1 = 0
            for(str in input) {
                if (count1 == 0) {
                    File(args[ListOfIndexFiles[i]]).writeText((str), Charsets.UTF_8)
                }
                else {
                    File(args[ListOfIndexFiles[i]]).appendText(("\n" + str), Charsets.UTF_8)
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
        flag_numberanalis = false
        flag_V = false
    }
}