import scala.io.StdIn.readLine
import controller.Controller

object Chess:

  def main(args: Array[String]): Unit =
    val controller = new Controller()
    val tui = new Tui(controller)

    Iterator
      .continually(readLine())
      .takeWhile(_ != "q")
      .foreach(tui.processInput)
