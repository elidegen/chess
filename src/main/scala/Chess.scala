import scala.io.StdIn.readLine
import controller.Controller
import aview.Tui
import model.{Chessboard, Classic}

object Chess:
  def main(args: Array[String]): Unit =
    val controller = Controller(Classic())
    val tui = Tui(controller)

    Iterator
      .continually(readLine())
      .takeWhile(_ != "quit")
      .foreach(tui.processInput)
