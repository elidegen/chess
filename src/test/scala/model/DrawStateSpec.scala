package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.rulesBaseImpl.DrawState
import model.rulesComponent.GameContext
import model.rulesComponent.MoveValidatorInterface
import model.rulesComponent.rulesBaseImpl.ClassicMoveValidator
import model.fileIOCompononent.FileIOInterface

class DrawStateSpec extends AnyWordSpec with Matchers:

  "DrawState" should {

    "expose the correct name" in {
      DrawState().name shouldBe "Draw!"
    }

    "ignore moves and return the same GameContext" in {
      val state = DrawState()
      val ctx = GameContext(board = Classic(), currentPlayer = White, state = state, gameMode = GameMode.Classic)

      given v: MoveValidatorInterface =
        ClassicMoveValidator()
      given f: FileIOInterface with
        override def load: GameContext =
          throw new UnsupportedOperationException("not used in this test")
        override def save(ctx: GameContext): Unit = ()

      val move = Move(Tile('a', 2), Tile('a', 3))
      val result = state.handleMove(ctx, move)

      result shouldBe theSameInstanceAs(ctx)
      result.board shouldBe ctx.board
      result.currentPlayer shouldBe ctx.currentPlayer
      result.state shouldBe ctx.state
    }
  }
