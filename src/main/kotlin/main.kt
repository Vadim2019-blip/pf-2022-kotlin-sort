import java.io.File

fun main(args: Array<String>) {
    var input =  mutableListOf<String>()
    val ListFlag = arrayOf("-f", "-r", "-u", "-V", "-o")
    var ListOfType = mutableListOf("")
    var ListOfFiles = mutableListOf("")

    for(arg in args){
        if (!(arg  in ListFlag)){
            ListOfFiles.add(ListOfFiles.size, arg)
            File(arg).useLines { lines -> lines.forEach { input.add(it) }}
        }

        else{
            ListOfType.add(ListOfType.size, arg)
            for(type in ListOfType){
                if(type == "-f"){
                    for(str in input){
                        println(str.toLowerCase())
                    }
                }
            }
        }
    }
}