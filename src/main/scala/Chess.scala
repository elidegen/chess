import scala.io.StdIn.readLine
import controller.Controller
import aview.Tui
import model.Chessboard

object Chess:
  def main(args: Array[String]): Unit =
    val controller = Controller(Chessboard.initial("classic"))
    val tui = Tui(controller)

    Iterator
      .continually(readLine())
      .takeWhile(_ != "q")
      .foreach(tui.processInput)
