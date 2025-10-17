import scala.io.StdIn.readLine

object Schach:
    def main(args:Array[String]) =
        println("\nWelcome to Chess!")
        try
            args(0).toInt
            println(createChessBoard(args(0).toInt))
        catch
            case _: Any =>
                val size = getSize()
                println(createChessBoard(size))

    def createChessBoard(size:Int):String = ("+" + " - +" * size + "\n" + "|   "* size + "|\n")* (size) + "+" + " - +" * size

    def getSize():Int =
        println("Spielbrettgröße in Int: ")
        while(true)
            val input = readLine()
            try
                return input.toInt
            catch
                case _: NumberFormatException =>
                    println("Ungültige eingabe!")
        0