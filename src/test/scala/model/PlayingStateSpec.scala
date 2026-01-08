package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayingStateSpec extends AnyWordSpec with Matchers:

  private def freshCtx(): GameContext =
    GameContext(board = Classic(), currentPlayer = White, state = PlayingState)

  "PlayingState.handleMove" should:

    "apply a valid move, switch the current player, and keep state as Playing" in:
      val ctx = freshCtx()
      val move = Move(Tile('a', 2), Tile('a', 3)) // a2a3

      val next = PlayingState.handleMove(ctx, move)

      next.board.getPiece(Tile('a', 2)) shouldBe Empty()
      next.board.getPiece(Tile('a', 3)) shouldBe Pawn(White)
      next.currentPlayer shouldBe Black
      next.state shouldBe PlayingState

    "ignore an illegal move and return the unchanged context" in:
      val ctx = freshCtx()
      val move = Move(Tile('a', 2), Tile('a', 5)) // illegal pawn move

      val next = PlayingState.handleMove(ctx, move)

      next shouldBe ctx
      next.board.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      next.board.getPiece(Tile('a', 5)) shouldBe Empty()
      next.currentPlayer shouldBe White
      next.state shouldBe PlayingState

    "ignore moves that start from an empty square" in:
      val ctx = freshCtx()
      val move = Move(Tile('a', 4), Tile('a', 5)) // a4 is empty in initial position

      val next = PlayingState.handleMove(ctx, move)

      next shouldBe ctx
      next.board.getPiece(Tile('a', 4)) shouldBe Empty()
      next.board.getPiece(Tile('a', 5)) shouldBe Empty()
      next.currentPlayer shouldBe White
      next.state shouldBe PlayingState
