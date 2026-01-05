import scala.io.StdIn.readLine
import controller.Controller
import aview.{Tui, Gui}
import model.{Classic, GameContext, White}
import model.PlayingState
import aview.Gui

object Chess:
  def main(args: Array[String]): Unit =
    val controller = Controller(
      GameContext(board = Classic(), currentPlayer = White, state = PlayingState))

    Gui.start(controller)
    val tui = Tui(controller)

    Iterator
      .continually(readLine())
      .takeWhile(_ != "quit")
      .foreach(tui.processInput)
