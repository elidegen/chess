package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.rulesComponent.GameContext
import model.domain.*
import model.rulesComponent.rulesBaseImpl.PlayingState
import controller.controllerComponent.controllerBaseImpl.Controller
import controller.controllerComponent.controllerBaseImpl.MoveCommand
import model.rulesComponent.MoveValidatorInterface
import model.rulesComponent.rulesBaseImpl.ClassicMoveValidator
import model.fileIOCompononent.FileIOInterface
import model.rulesComponent.RulesInterface
import model.rulesComponent.rulesBaseImpl.Rules

class MoveCommandSpec extends AnyWordSpec with Matchers:

  private def freshCtx(): GameContext =
    GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic)
  given v: MoveValidatorInterface =
    ClassicMoveValidator()
  given f: FileIOInterface with
    override def load: GameContext =
      throw new UnsupportedOperationException("not used in this test")

    override def save(ctx: GameContext): Unit = ()
  given r: RulesInterface = 
    Rules()
  "MoveCommand" should:

    "change controller.ctx to proper context (doStep)" in:
      val before = freshCtx()
      val controller = Controller(before)

      val after = before.handleMove(Move(Tile('a', 2), Tile('a', 3)))
      after should not be before

      val cmd = new MoveCommand(controller, after)

      cmd.doStep
      controller.ctx shouldBe after

    "restore the previous context (undoStep)" in:
      val before = freshCtx()
      val controller = Controller(before)

      val after = before.handleMove(Move(Tile('a', 2), Tile('a', 3)))
      after should not be before

      val cmd = new MoveCommand(controller, after)

      cmd.doStep
      controller.ctx shouldBe after

      cmd.undoStep
      controller.ctx shouldBe before

    "redo what has been undone (redoStep)" in:
      val before = freshCtx()
      val controller = Controller(before)

      val after = before.handleMove(Move(Tile('a', 2), Tile('a', 3)))
      after should not be before

      val cmd = new MoveCommand(controller, after)

      cmd.doStep
      cmd.undoStep
      controller.ctx shouldBe before

      cmd.redoStep
      controller.ctx shouldBe after
