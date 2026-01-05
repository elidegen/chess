package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.*

class MoveCommandSpec extends AnyWordSpec with Matchers:

  private def freshCtx(): GameContext =
    GameContext(board = Classic(), currentPlayer = White, state = PlayingState)

  "MoveCommand" should:

    "set controller.ctx to the provided after-context when executed (doStep)" in:
      val before = freshCtx()
      val controller = Controller(before)

      val move = Move(Tile('a', 2), Tile('a', 3))
      val after = before.handleMove(move)
      after should not be before // sanity check: move must actually change the context

      val cmd = new MoveCommand(controller, after)

      cmd.doStep
      controller.ctx shouldBe after

    "restore the previous context on undoStep" in:
      val before = freshCtx()
      val controller = Controller(before)

      val after = before.handleMove(Move(Tile('a', 2), Tile('a', 3)))
      after should not be before

      val cmd = new MoveCommand(controller, after)

      cmd.doStep
      controller.ctx shouldBe after

      cmd.undoStep
      controller.ctx shouldBe before

    "re-apply the after-context on redoStep" in:
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
