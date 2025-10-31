import scala.io.StdIn.readLine

object Chess:
    def main(args:Array[String]) =
        println(init(args))

    def createChessBoard(size:Int):String = ("+" + " - +" * size + "\n" + "|   "* size + "|\n")* (size) + "+" + " - +" * size

    def getSize(input: () => String = readLine):Int =
        println("Spielbrettgröße in Int: ")
        input().toIntOption.getOrElse(getSize(input))

    def init(args:Array[String]):String =
        println("\nWelcome to Chess")
        val size =
        args.headOption
            .flatMap(_.toIntOption)
            .getOrElse(getSize())
        createChessBoard(size)