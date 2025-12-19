import scala.io.StdIn.readLine
import controller.Controller
import aview.Tui
import model.{Chessboard, Classic, GameContext, White}
import model.PlayingState

object Chess:
  def main(args: Array[String]): Unit =
    val controller = Controller(
      GameContext(board = Classic(), currentPlayer = White, state = PlayingState))
    val tui = Tui(controller)

    Iterator
      .continually(readLine())
      .takeWhile(_ != "quit")
      .foreach(tui.processInput)
