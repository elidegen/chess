package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.{GameContext, MoveValidatorInterface}
import model.rulesComponent.rulesBaseImpl.{PlayingState, ClassicMoveValidator}

class ClassicSpec extends AnyWordSpec with Matchers:

  private def freshBoard(): Classic = Classic()

  private def freshCtx(): GameContext =
    GameContext(board = freshBoard(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic)

  given v: MoveValidatorInterface =
    ClassicMoveValidator()

  "Classic" should:

    "create the classic start position" in:
      val board = freshBoard()

      board.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      board.getPiece(Tile('b', 2)) shouldBe Pawn(White)
      board.getPiece(Tile('a', 7)) shouldBe Pawn(Black)
      board.getPiece(Tile('b', 7)) shouldBe Pawn(Black)

      board.getPiece(Tile('e', 1)) shouldBe King(White)
      board.getPiece(Tile('e', 8)) shouldBe King(Black)

      board.getPiece(Tile('d', 1)) shouldBe Queen(White)
      board.getPiece(Tile('d', 8)) shouldBe Queen(Black)

    "return a new Classic board instance after move" in:
      val board = freshBoard()
      val moved = board.move(Move(Tile('a', 2), Tile('a', 3)))

      moved shouldBe a[Classic]

  "ClassicMoveValidator" should:

    "allow a legal white move when it is White's turn" in:
      val ctx = freshCtx()

      val ok = summon[MoveValidatorInterface].validate(ctx, Move(Tile('a', 2), Tile('a', 3)))
      ok shouldBe true

    "reject a move by the wrong player" in:
      val ctx = freshCtx()

      val ok = summon[MoveValidatorInterface].validate(ctx, Move(Tile('a', 7), Tile('a', 6)))
      ok shouldBe false

    "reject an illegal pawn move" in:
      val ctx = freshCtx()

      val ok = summon[MoveValidatorInterface].validate(ctx, Move(Tile('a', 2), Tile('a', 5)))
      ok shouldBe false

    "apply a legal move on the board and update pieces accordingly" in:
      val ctx = freshCtx()

      val move = Move(Tile('a', 2), Tile('a', 3))
      summon[MoveValidatorInterface].validate(ctx, move) shouldBe true

      val newBoard = ctx.board.move(move)
      newBoard.getPiece(Tile('a', 2)) shouldBe Empty()
      newBoard.getPiece(Tile('a', 3)) shouldBe Pawn(White)
