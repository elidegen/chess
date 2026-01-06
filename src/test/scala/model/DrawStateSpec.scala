package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DrawStateSpec extends AnyWordSpec with Matchers:

  "DrawState" should {

    "expose the correct name" in {
      DrawState().name shouldBe "Draw!"
    }

    "ignore moves and return the same GameContext" in {
      val state = DrawState()
      val ctx = GameContext(board = Classic(), currentPlayer = White, state = state)

      val move = Move(Tile('a', 2), Tile('a', 3))
      val result = state.handleMove(ctx, move)

      result shouldBe theSameInstanceAs(ctx)
      result.board shouldBe ctx.board
      result.currentPlayer shouldBe ctx.currentPlayer
      result.state shouldBe ctx.state
    }
  }
