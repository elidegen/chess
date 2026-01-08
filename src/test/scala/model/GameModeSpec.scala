package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameModeSpec extends AnyWordSpec with Matchers:

  "GameFactory.newGame" should:

    "create a fresh Classic game context" in:
      val ctx = GameFactory.newGame(GameMode.Classic)

      ctx.currentPlayer shouldBe White
      ctx.state shouldBe PlayingState

      ctx.board shouldBe a[Classic]

    "create the classic starting position" in:
      val ctx = GameFactory.newGame(GameMode.Classic)
      val b = ctx.board

      b.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      b.getPiece(Tile('e', 1)) shouldBe King(White)
      b.getPiece(Tile('d', 1)) shouldBe Queen(White)

      b.getPiece(Tile('a', 7)) shouldBe Pawn(Black)
      b.getPiece(Tile('e', 8)) shouldBe King(Black)
      b.getPiece(Tile('d', 8)) shouldBe Queen(Black)

      b.getPiece(Tile('e', 4)) shouldBe Empty()
      b.getPiece(Tile('d', 5)) shouldBe Empty()

    // "return a new independent context each time" in:
    //   val ctx1 = GameFactory.newGame(GameMode.Classic)
    //   val ctx2 = GameFactory.newGame(GameMode.Classic)

    //   ctx1 should not be theSameInstanceAs(ctx2)

    //   val moved = ctx1.handleMove(Move(Tile('a', 2), Tile('a', 3)))

    //   moved.board.getPiece(Tile('a', 2)) shouldBe Empty()
    //   moved.board.getPiece(Tile('a', 3)) shouldBe Pawn(White)

    //   ctx2.board.getPiece(Tile('a', 2)) shouldBe Pawn(White)
    //   ctx2.board.getPiece(Tile('a', 3)) shouldBe Empty()
