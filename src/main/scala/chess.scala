import scala.io.StdIn.readLine

object Schach:
    def main(args:Array[String]) =
        println("\nWelcome to Chess?")
        val size =
        args.headOption
            .flatMap(_.toIntOption)
            .getOrElse(getSize())
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