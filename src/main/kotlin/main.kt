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
-f, -n, -o, -r, -u, -random, -numberanalis, -V, -ws, -innfoK
2.1) Флаг -V поддерживает сортировку по трем точкам. Если точек больше, то ошибки тоже не будет, однако результат сортировки может быть не таким каким нужно
2.2) Флаг -numberanalis создан в качестве дополнение к флагу -n, он служит для вывода информации о числах в файле, для удобства ввыводит в окно Run, ведь если бы он записывал в файл, то потом было бы трудно применять другие флаги
2.3) -ws Собственный флаг. Можно сортировать даже данные вида несколько строк, разделенных пробелом(Ровно одним)
2.4) -infoK Собственный флаг, нужен чтобы выдавать пользовутелю элемент с номером k в отсортированном файле. ПОддерживает вывод в файл или в Run.
3) Примеры ввода данных:
input -f file -f -o -r -u number -n endinput
input -f -r file -f number -n endinput
4) Файлы должны быть созданы или лежать в папке с проектом, исходно там лежат input, file, number и Versionn
5) флаг -random можно использовать только без других флагов
6) Названия файлов не могут начинаться на -
7) Флаг -random простой, но очень полезный, благодаря нему можно не вбивать данные в файл после сортировки

-------------------------------------------------------------------
*/

/*Функция, которая проверяет является ли строка числом нужна для реализации флага -n */
fun isNumber(a: String): Boolean { /*Так же поддерживается формат отрицательных чисел*/
    if(a[0] == '-' && a.substring(1, a.length).all { it.isDigit() } || a.all { it.isDigit() }){
        return true
    }
    return false
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
    val pointindex = mutableListOf<Int>()
    pointindex.add(-1)
    for (j in 0..string1.length - 1) {
        if (string1[j] == '.') {
            pointindex.add(j)
        }
    }
    for (i in 1..pointindex.size - 1) {
        val curr1 = string1.substring(pointindex[i - 1] + 1, pointindex[i])
        if (!isNumber(curr1)) {
            ans = false
            break
        }
    }
    val currlast: String
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
    val indexpoint = mutableListOf<Int>()
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


/*Функция для сортировки с флагом -o*/
fun flag_oSort(ListOfIndexFiles: MutableList<Int>, args: Array<String>, input: MutableList<String>, i: Int){
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

/*Функция для сортировки с флагом -V*/

fun flag_VSort(input: MutableList<String>): MutableList<String>{
    val versions = mutableListOf<String>()
    val notversion = mutableListOf<String>()
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
            if(ParsVers(versions[i]) > ParsVers(versions[j])){
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
    return input
}

/*Функция для сортировки с флагом -r*/
fun flag_rSort(input: MutableList<String>): MutableList<String>{
    return input.asReversed()
}

/*Функция для сортировки с флагом -ws*/
fun flag_wsSort(input: MutableList<String>): MutableList<String>{
    for(i in 0..input.size - 1){
        val list = input[i].split(" ")
        input.removeAt(i)
        for(j in 0..list.size - 1){
            input.add(list[j])
        }
    }
    return input
}

/*Функция для сортировки с флагом -info*/
fun flag_info(input: MutableList<String>, k : Int): String{
    return input[k - 1]
}
/* Функция для проверки существования Файла в системе, что бы программа не падала*/
fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}

/*Функция для сортировки с игнорированием регистра */
fun IgnorCase(input: MutableList<String>): MutableList<String>{
    input.sortWith(String.CASE_INSENSITIVE_ORDER)
    return input
}

fun main(args: Array<String>) {
    var digit = 0

    var input = mutableListOf<String>()

    val ListOfIndexFiles = mutableListOf<Int> ()
    var count = 0
    /*создаем переменные, которые отвечают за наличие флашов*/
    var flag = false
    var flag_o = false
    var flag_r = false
    var flag_n  = false
    var flag_numberanalis = false
    var flag_random = false
    var flag_V = false
    var flag_wspace = false
    var flag_info = false

    /*Список со всеми индексами названий файлов*/
    /* обратавыем флаги и названия файлов, формируем список с индексами названий файлов*/
    for (arg in args) {
        if (arg[0] != '-') {
            ListOfIndexFiles.add(count)
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
            else if(type == "-V") {
                flag_V = true
            }
            else if(type.length >= 6 && type.substring(0, 5) == "-info"){
                digit = type.substring(5, type.length).toInt()
                if(!isNumber(type.substring(5, type.length))){
                    println("Флаг -info нельзя пременить, потому что $digit не является числом")
                }
                else if(digit > input.size){
                    println("Флаг нельзя пременить по причине $digit больше длины файла")
                }
                else{
                    flag_info = true
                }
            }
            else if (type == "-ws"){
                flag_wspace = true
                }
            else{
                println("$type программа не поддерживает такой флаг")
            }
        }
        /*Далее идет действие применения флагов к файлам*/
        if(flag_wspace){
            flag_wsSort(input)
        }
        if(flag){
            IgnorCase(input)
        }
        else input.sort()
        if(flag_n){
            val inputcopy = mutableListOf<Int>()
            val inputcopy2 = mutableListOf<String>()
            for(str in input){
                if(isNumber(str)){
                    val strt = str.toInt()
                    inputcopy.add(strt)
                }
                else{
                    inputcopy2.add(str)
                }
            }
            val c = args[ListOfIndexFiles[i]]
            if(inputcopy.size == 0){
                println("В файле $c нет цифр, поэтому флаг -n пременить нельзя")
            }
            else {
                input.clear()
                inputcopy.sort()
                if (flag_numberanalis){
                    var sum = 0
                    val min = inputcopy[0]
                    var median: Int
                    if((inputcopy.size - 1) % 2 == 0){
                        median = inputcopy[(inputcopy.size - 1)/2]
                    }
                    else{
                        median =  inputcopy[(inputcopy.size)/2]
                    }
                    val max = inputcopy[inputcopy.size -1]
                    for(i in 0 until inputcopy.size) {
                        sum += inputcopy[i]
                    }
                    val mean = sum/inputcopy.size
                    println("Максимум: $max , Минимум: $min, Медиана: $median, Сумма: $sum, Среднее: $mean")
                }
                inputcopy2.sort()
                for (i in 0 until inputcopy.size) {
                    input.add(inputcopy[i].toString())
                }
                for (i in inputcopy.size until inputcopy.size + inputcopy2.size) {
                    input.add(inputcopy2[i - inputcopy.size])
                }
            }
        }
        if (flag_r) {
            flag_rSort(input)
            }
        if(flag_random){
            input.shuffle()
        }
        if(flag_V){
            flag_VSort(input)
        }
        if(flag_info) {
            if (flag_o == false){
                println("Результат запроса с номером $digit выдал " + flag_info(input, digit))
            }
            else{
                val addstring = "Результат запроса с номером $digit выдал " + flag_info(input, digit)
                input.add(addstring)
            }
        }
        if(flag_o) {
            flag_oSort(ListOfIndexFiles, args, input, i)
        }
        else {
            println(input)
        }
        /*Последняя часть input очищает и переменные, отвечающие за наличие флагов становятся равны false*/
        input.clear()
        flag = false
        flag_o = false
        flag_r = false
        flag_n = false
        flag_numberanalis = false
        flag_V = false
        flag_wspace = false
        flag_info = false
    }
}