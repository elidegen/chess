package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

final class CheckMateStateSpec extends AnyWordSpec with Matchers:

  private def ctxWith(state: GameState, current: Color = White): GameContext =
    GameContext(board = Classic(), currentPlayer = current, state = state)

  "CheckmateState" should:

    "indicate winner" in:
      val stateWhiteInCheck = CheckmateState(ctxWith(PlayingState), playerInCheck = White)
      stateWhiteInCheck.name shouldBe "Checkmate! Black won!"

      val stateBlackInCheck = CheckmateState(ctxWith(PlayingState), playerInCheck = Black)
      stateBlackInCheck.name shouldBe "Checkmate! White won!"

    "reject further moves by returning context unchanged" in:
      val state = CheckmateState(ctxWith(PlayingState), playerInCheck = White)
      val ctx = ctxWith(state)

      val beforeBoard = ctx.board
      val beforePlayer = ctx.currentPlayer
      val beforeState = ctx.state

      val result = state.handleMove(ctx, Move(Tile('a', 2), Tile('a', 3)))

      result shouldBe ctx
      result.board shouldBe beforeBoard
      result.currentPlayer shouldBe beforePlayer
      result.state shouldBe beforeState
