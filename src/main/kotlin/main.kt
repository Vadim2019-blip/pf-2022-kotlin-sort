

fun main(args: Array<String>) {
    val lenparse = args.size
    val ListFlag = arrayOf("-f", "-r", "-u", "-V", "-o")
    val ListOfType = arrayOf("")
    val ListOfFiles = arrayOf("")

    for(arg in args){
        if (arg in ListFlag){
            ListOfType+arg
        }
        else{
            ListOfFiles+arg
        }
    }
    for(i in ListOfType){
        println(i)
    }
    for(j in ListOfType){
        println(j)
    }
}