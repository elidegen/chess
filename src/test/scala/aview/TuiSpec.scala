package aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.{GameContext, MoveValidatorInterface, RulesInterface}
import model.rulesComponent.rulesBaseImpl.{PlayingState, ClassicMoveValidator, Rules}
import controller.controllerComponent.ControllerInterface
import controller.controllerComponent.controllerBaseImpl.Controller
import model.fileIOCompononent.FileIOInterface


class TuiSpec extends AnyWordSpec with Matchers:
  given v: MoveValidatorInterface =
    ClassicMoveValidator()
  given f: FileIOInterface with
    override def load: GameContext =
      throw new UnsupportedOperationException("not used in this test")
    override def save(ctx: GameContext): Unit = ()
  given r: RulesInterface = 
    Rules()
  "processInput(String)" should:
    "create new chessboard" in:
      val controller =
        Controller(GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic))
      val tui = Tui(controller)
      controller.chessboard.move(Move(Tile('a', 2), Tile('a', 3)))
      tui.processInput("n")
      tui.processInput("1")
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(White)
    "make a move" in:
      val controller =
        Controller(GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic))
      val tui = Tui(controller)
      tui.processInput("a2a3")
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Pawn(White)
    "do nothing" in:
      val controller =
        Controller(GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic))
      val tui = Tui(controller)
      val before = controller.chessboard
      tui.processInput("q")
      controller.chessboard shouldBe before
  "add" should:
    "add tui to observer list of controller" in:
      val controller =
        Controller(GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic))
      val tui = Tui(controller)
      controller.add(tui)
      controller.subscribers.length shouldBe 2
  "remove" should:
    "add tui to observer list of controller" in:
      val controller =
        Controller(GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic))
      val tui = Tui(controller)
      controller.add(tui)
      controller.remove(tui)
      controller.subscribers.length shouldBe 0
