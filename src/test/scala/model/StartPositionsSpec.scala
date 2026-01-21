package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*

class StartPositionsSpec extends AnyWordSpec with Matchers:

  "StartPositions.classic" should:

    "contain exactly 64 tiles (8x8)" in:
      StartPositions.classic.size shouldBe 64

    "contain a piece entry for every tile from a1 to h8" in:
      val expected =
        (for
          x <- 'a' to 'h'
          y <- 1 to 8
        yield Tile(x, y)).toSet

      StartPositions.classic.keySet shouldBe expected

    "place white pawns on rank 2 and black pawns on rank 7" in:
      for x <- 'a' to 'h' do
        StartPositions.classic(Tile(x, 2)) shouldBe Pawn(White)
        StartPositions.classic(Tile(x, 7)) shouldBe Pawn(Black)

    "place the correct white back rank pieces on rank 1" in:
      StartPositions.classic(Tile('a', 1)) shouldBe Rook(White)
      StartPositions.classic(Tile('b', 1)) shouldBe Knight(White)
      StartPositions.classic(Tile('c', 1)) shouldBe Bishop(White)
      StartPositions.classic(Tile('d', 1)) shouldBe Queen(White)
      StartPositions.classic(Tile('e', 1)) shouldBe King(White)
      StartPositions.classic(Tile('f', 1)) shouldBe Bishop(White)
      StartPositions.classic(Tile('g', 1)) shouldBe Knight(White)
      StartPositions.classic(Tile('h', 1)) shouldBe Rook(White)

    "place the correct black back rank pieces on rank 8" in:
      StartPositions.classic(Tile('a', 8)) shouldBe Rook(Black)
      StartPositions.classic(Tile('b', 8)) shouldBe Knight(Black)
      StartPositions.classic(Tile('c', 8)) shouldBe Bishop(Black)
      StartPositions.classic(Tile('d', 8)) shouldBe Queen(Black)
      StartPositions.classic(Tile('e', 8)) shouldBe King(Black)
      StartPositions.classic(Tile('f', 8)) shouldBe Bishop(Black)
      StartPositions.classic(Tile('g', 8)) shouldBe Knight(Black)
      StartPositions.classic(Tile('h', 8)) shouldBe Rook(Black)

    "place Empty on all middle squares (ranks 3 to 6)" in:
      for
        x <- 'a' to 'h'
        y <- 3 to 6
      do StartPositions.classic(Tile(x, y)) shouldBe Empty()
