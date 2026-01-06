package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CheckStateSpec extends AnyWordSpec with Matchers:

  private def startCtx: GameContext =
    GameContext(board = Classic(), currentPlayer = White, state = PlayingState)

  private def makeMove(from: Tile, to: Tile): Move =
    Move(Tile(from.x, from.y), Tile(to.x, to.y))

  private def putBlackInCheck(): GameContext =
    val ctx1 = PlayingState.handleMove(startCtx, makeMove(('e', 2), ('e', 4)))
    val ctx2 = ctx1.state.handleMove(ctx1, makeMove(('f', 7), ('f', 6)))
    val ctx3 = ctx2.state.handleMove(ctx2, makeMove(('d', 1), ('h', 5)))
    ctx3

  "CheckState" should:

    "be reached when a move puts the opponent into check (sanity check)" in:
      val ctxInCheck = putBlackInCheck()

      ctxInCheck.currentPlayer shouldBe Black
      ctxInCheck.state match
        case CheckState(_, playerInCheck) => playerInCheck shouldBe Black
        case other => fail(s"Expected CheckState, got: ${other}")

    "reject an invalid move (validateMove == false) and keep context unchanged" in:
      val ctxInCheck = putBlackInCheck()

      val before = ctxInCheck
      val result =
        ctxInCheck.state.handleMove(ctxInCheck, makeMove(('a', 7), ('a', 9))) // invalid rank

      result shouldBe before

    "reject a move that does not resolve the check (should not change board or turn)" in:
      val ctxInCheck = putBlackInCheck()

      // a7a6 does not block/capture the checking queen or move the king.
      val before = ctxInCheck
      val result = ctxInCheck.state.handleMove(ctxInCheck, makeMove(('a', 7), ('a', 6)))

      // Expected: still Black to move, still in Check, and board unchanged.
      result.currentPlayer shouldBe before.currentPlayer
      result.board shouldBe before.board
      result.state match
        case CheckState(_, playerInCheck) => playerInCheck shouldBe Black
        case other => fail(s"Expected CheckState, got: ${other}")

    "accept a move that resolves the check and transition to PlayingState" in:
      val ctxInCheck = putBlackInCheck()

      // g7g6 blocks the diagonal h5-g6-f7-e8.
      val result = ctxInCheck.state.handleMove(ctxInCheck, makeMove(('g', 7), ('g', 6)))

      result.currentPlayer shouldBe White
      result.state shouldBe PlayingState

      // Verify the blocking pawn moved.
      result.board.getPiece(Tile('g', 7)) shouldBe Empty()
      result.board.getPiece(Tile('g', 6)) shouldBe Pawn(Black)
