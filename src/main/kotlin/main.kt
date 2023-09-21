import java.io.File


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


fun main(args: Array<String>) {

    var input = mutableListOf<String>()
    val ListFlag = arrayOf("-f", "-r", "-u", "-V", "-o")
    var ListOfType = mutableListOf("")
    var ListOfFiles = mutableListOf("")
    var ListOfIndexFiles = mutableListOf(0)
    var count = 0
    var flag = false
    var flag_o = false
    var flag_f = false

    for (arg in args) {
        if (!(arg in ListFlag)) {
            ListOfIndexFiles.add(count)
        }
        count += 1
    }
    println(ListOfIndexFiles)
    for(i in 1..ListOfIndexFiles.size - 2){
        File(args[ListOfIndexFiles[i]]).useLines { lines -> lines.forEach { input.add(it) } }

        for(type in args.slice(ListOfIndexFiles[i]..ListOfIndexFiles[i+1])){
            if(type == "-f"){
                for(i in 0..input.size - 1){
                    input[i] = input[i].toLowerCase()
                }
            }
            if(type == "-r"){
                flag = true
            }
            if(type == "-u"){
               val c = input.toMutableSet()
                input = c.toMutableList()
            }
            if(type == "-o"){
                flag_o = true
            }
            if(type == "-f"){
                flag = true
            }
            if(type == "-n"){
                TODO()
            }
        }
        if(flag_f){
            input.sortBy{it.toLowerCase()}
        }
        else{
            input.sort()
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
            if (flag == false) {
                println(input)
            } else {
                println(input.asReversed())
            }
        }
        input.clear()
        flag = false
        flag_o = false
        flag_f = false
    }
}