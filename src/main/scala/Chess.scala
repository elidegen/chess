import scala.io.StdIn.readLine
import aview.{Tui, Gui}
import controller.controllerComponent.ControllerInterface
import controller.controllerComponent.controllerBaseImpl.{Controller}
import model.rulesComponent.rulesBaseImpl.{GameContext, PlayingState}
import model.dataComponent.dataBaseImpl.{Color, Classic, Move, White}
import ChessModule.given

object Chess:
  def main(args: Array[String]): Unit =
    // val controller: ControllerInterface = Controller(
    //   GameContext(board = Classic(), currentPlayer = White, state = PlayingState))

    val controller = summon[ControllerInterface]
    Gui.start(controller)
    val tui = Tui(controller)
    // controller.newGame()
    Iterator
      .continually(Option(readLine()))
      .takeWhile(_.exists(_ != "quit"))
      .flatten
      .foreach(tui.processInput)
