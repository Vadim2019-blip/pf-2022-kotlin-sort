import java.io.File

fun main(args: Array<String>) {
    var input =  mutableListOf<String>()
    val ListFlag = arrayOf("-f", "-r", "-u", "-V", "-o")
    var ListOfType = mutableListOf("")
    var flag = false
    var c = args[0]
    var count = 0

    File(args[0]).useLines { lines -> lines.forEach { input.add(it) } }


    for(arg in args.slice(1..args.size - 1)) {
        count += 1
        if (!(arg in ListFlag)) {
            for (type in ListOfType) {
                if (type == "-f") {
                    for (str in input) {
                        str.lowercase()
                    }
                }
                if (type == "-r") {
                    flag = true
                }
            }
            println("$c с операциями $ListOfType")
            for (i in 0..input.size - 1) {
                if (flag) {
                    println(input[input.size - i])
                } else {
                    println(input[i])
                }
            }
            input.clear()
            File(arg).useLines { lines -> lines.forEach { input.add(it) } }
            ListOfType.clear()
            c = arg

        }
        else if(count == args.size - 1){
            for (type in ListOfType) {
                if (type == "-f") {
                    for (str in input) {
                        str.lowercase()
                    }
                }
                if (type == "-r") {
                    flag = true
                }
            }
            println("$c с операциями $ListOfType")
            for (i in 0..input.size - 1) {
                if (flag) {
                    println(input[input.size - i -1])
                } else {
                    println(input[i])
                }
            }
        }
        else {
            ListOfType.add(ListOfType.size, arg)
        }
    }
}