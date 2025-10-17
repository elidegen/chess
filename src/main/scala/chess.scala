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
        val input = readLine()
        input.toIntOption.getOrElse(getSize())