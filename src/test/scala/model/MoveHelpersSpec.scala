package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.GameContext
import model.rulesComponent.rulesBaseImpl.PlayingState
import model.rulesComponent.rulesBaseImpl.MoveHelpers

class MoveHelpersSpec extends AnyWordSpec with Matchers:

  private def ctxClassic: GameContext =
    GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic)

  "MoveHelpers.charToNr / digitToNr" should {
    "convert tiles to 0-based coordinates" in {
      MoveHelpers.charToNr(Tile('a', 1)) shouldBe 0
      MoveHelpers.charToNr(Tile('h', 8)) shouldBe 7

      MoveHelpers.digitToNr(Tile('a', 1)) shouldBe 0
      MoveHelpers.digitToNr(Tile('a', 8)) shouldBe 7
    }
  }

  "MoveHelpers.deltaX / deltaY" should {
    "compute deltas between from and to tiles" in {
      val m1 = Move(Tile('a', 1), Tile('c', 3))
      MoveHelpers.deltaX(m1) shouldBe 2
      MoveHelpers.deltaY(m1) shouldBe 2

      val m2 = Move(Tile('h', 8), Tile('f', 7))
      MoveHelpers.deltaX(m2) shouldBe -2
      MoveHelpers.deltaY(m2) shouldBe -1

      val m3 = Move(Tile('d', 4), Tile('d', 1))
      MoveHelpers.deltaX(m3) shouldBe 0
      MoveHelpers.deltaY(m3) shouldBe -3
    }
  }

  "MoveHelpers.sign" should {
    "return -1, 0, or 1" in {
      MoveHelpers.sign(-10) shouldBe -1
      MoveHelpers.sign(0) shouldBe 0
      MoveHelpers.sign(7) shouldBe 1
    }
  }

  "MoveHelpers.abs" should {
    "return the absolute value" in {
      MoveHelpers.abs(-5) shouldBe 5
      MoveHelpers.abs(0) shouldBe 0
      MoveHelpers.abs(9) shouldBe 9
    }
  }

  "MoveHelpers.isEmptyAt" should {
    "detect empty and non-empty squares in the classic start position" in {
      val ctx = ctxClassic

      // a2 contains a white pawn at start
      MoveHelpers.isEmptyAt(ctx, Tile('a', 2)) shouldBe false

      // a3 is empty at start
      MoveHelpers.isEmptyAt(ctx, Tile('a', 3)) shouldBe true
    }
  }

  "MoveHelpers.pieceColor" should {
    "return the color for non-empty pieces" in {
      MoveHelpers.pieceColor(Pawn(White)) shouldBe White
      MoveHelpers.pieceColor(King(Black)) shouldBe Black
      MoveHelpers.pieceColor(Queen(White)) shouldBe White
    }

    "throw for Empty" in {
      intercept[IllegalArgumentException] {
        MoveHelpers.pieceColor(Empty())
      }
    }
  }

  "MoveHelpers.isEnemyAt / isOwnPieceAt" should {
    "classify pieces on a tile relative to a color" in {
      val ctx = ctxClassic

      // White pawn on a2
      MoveHelpers.isOwnPieceAt(ctx, Tile('a', 2), White) shouldBe true
      MoveHelpers.isEnemyAt(ctx, Tile('a', 2), White) shouldBe false

      // Black pawn on a7
      MoveHelpers.isOwnPieceAt(ctx, Tile('a', 7), White) shouldBe false
      MoveHelpers.isEnemyAt(ctx, Tile('a', 7), White) shouldBe true

      // Empty square
      MoveHelpers.isOwnPieceAt(ctx, Tile('a', 3), White) shouldBe false
      MoveHelpers.isEnemyAt(ctx, Tile('a', 3), White) shouldBe false
    }
  }

  "MoveHelpers.intermediateTilesOnLine" should {
    "return intermediate tiles for rook-like moves" in {
      MoveHelpers.intermediateTilesOnLine(Tile('a', 1), Tile('a', 4)) shouldBe
        List(Tile('a', 2), Tile('a', 3))

      MoveHelpers.intermediateTilesOnLine(Tile('d', 4), Tile('g', 4)) shouldBe
        List(Tile('e', 4), Tile('f', 4))
    }

    "return intermediate tiles for bishop-like moves" in {
      MoveHelpers.intermediateTilesOnLine(Tile('c', 1), Tile('f', 4)) shouldBe
        List(Tile('d', 2), Tile('e', 3))

      MoveHelpers.intermediateTilesOnLine(Tile('h', 8), Tile('e', 5)) shouldBe
        List(Tile('g', 7), Tile('f', 6))
    }

    "return Nil for non-linear moves or identical from/to" in {
      // knight-like
      MoveHelpers.intermediateTilesOnLine(Tile('b', 1), Tile('c', 3)) shouldBe Nil

      // not on a diagonal
      MoveHelpers.intermediateTilesOnLine(Tile('a', 1), Tile('b', 3)) shouldBe Nil

      // same square
      MoveHelpers.intermediateTilesOnLine(Tile('a', 1), Tile('a', 1)) shouldBe Nil
    }
  }

  "MoveHelpers.isPathClear" should {
    "return true when there are no intermediate blockers" in {
      val ctx = ctxClassic

      // Adjacent squares have no intermediate tiles; path-clear only checks intermediate squares.
      MoveHelpers.isPathClear(ctx, Tile('a', 1), Tile('a', 2)) shouldBe true
      MoveHelpers.isPathClear(
        ctx,
        Tile('b', 1),
        Tile('c', 3)
      ) shouldBe true // intermediate list is Nil
    }

    "return false when intermediate squares are occupied" in {
      val ctx = ctxClassic

      // a-file is blocked by the pawn on a2
      MoveHelpers.isPathClear(ctx, Tile('a', 1), Tile('a', 8)) shouldBe false

      // bishop from c1 to h6 is blocked by the pawn on d2
      MoveHelpers.isPathClear(ctx, Tile('c', 1), Tile('h', 6)) shouldBe false
    }
  }
