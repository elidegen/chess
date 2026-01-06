package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MoveValidatorSpec extends AnyWordSpec with Matchers:

  // Local helper to avoid depending on any MoveHelpers implementation details.
  private def colorOf(p: Piece): Color =
    p match
      case Pawn(c) => c
      case Rook(c) => c
      case Knight(c) => c
      case Bishop(c) => c
      case Queen(c) => c
      case King(c) => c
      case _: Empty => throw new IllegalArgumentException("Empty has no color")

  /**
   * Minimal concrete validator for tests.
   *   - Uses ClassicStrategyProvider to test Strategy-based move legality.
   *   - Default: no check/draw/checkmate logic.
   */
  private class TestValidator(
      override protected val strategies: StrategyProvider = ClassicStrategyProvider,
      checkFn: (Chessboard, Color) => Boolean = (_, _) => false,
      specificRulesFn: (GameContext, Piece, Move) => Boolean = (_, _, _) => true)
      extends MoveValidator:

    override protected def isCorrectPlayer(ctx: GameContext, piece: Piece): Boolean =
      colorOf(piece) == ctx.currentPlayer

    override protected def passSpecificRules(
        ctx: GameContext,
        piece: Piece,
        move: Move): Boolean =
      specificRulesFn(ctx, piece, move)

    override def isCheck(board: Chessboard, color: Color): Boolean = checkFn(board, color)
    override def isDraw(board: Chessboard, color: Color): Boolean = false
    override def isCheckmate(board: Chessboard, color: Color): Boolean = false

  private def freshCtx(validator: MoveValidator, current: Color = White): GameContext =
    GameContext(
      board = Classic().copy(validator = validator),
      currentPlayer = current,
      state = PlayingState)

  "MoveValidator.validate" should:

    "reject a move if the from-tile is empty" in:
      val v = new TestValidator()
      val ctx = freshCtx(v)

      val move = Move(Tile('a', 3), Tile('a', 4)) // a3 is empty in initial position
      v.validate(ctx, move) shouldBe false

    "reject a move if it is not the correct player's piece" in:
      val v = new TestValidator()
      val ctx = freshCtx(v, current = Black) // but a2 has a white pawn

      val move = Move(Tile('a', 2), Tile('a', 3))
      v.validate(ctx, move) shouldBe false

    "reject a move if the piece move strategy deems it illegal" in:
      val v = new TestValidator()
      val ctx = freshCtx(v)

      // Pawn cannot move 3 squares.
      val move = Move(Tile('a', 2), Tile('a', 5))
      v.validate(ctx, move) shouldBe false

    "accept a legal basic move" in:
      val v = new TestValidator()
      val ctx = freshCtx(v)

      val move = Move(Tile('a', 2), Tile('a', 3))
      v.validate(ctx, move) shouldBe true

    "reject a move that would leave the current player's king in check" in:
      // Force check after any move
      val v = new TestValidator(checkFn = (_, _) => true)
      val ctx = freshCtx(v)

      val move = Move(Tile('a', 2), Tile('a', 3))
      v.validate(ctx, move) shouldBe false

    "reject a move if variant-specific rules fail" in:
      val v = new TestValidator(specificRulesFn = (_, _, _) => false)
      val ctx = freshCtx(v)

      val move = Move(Tile('a', 2), Tile('a', 3))
      v.validate(ctx, move) shouldBe false
