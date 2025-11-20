package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model._

class ChessboardSpec extends AnyWordSpec with Matchers:
  "getPiece(Tile)" should:
    val cb = Chessboard.initial
    "return the piece located in Tile" in:
      cb.getPiece(Tile('a', 2)) shouldBe Pawn(false)
      cb.getPiece(Tile('e', 1)) shouldBe King(false)
      cb.getPiece(Tile('d', 8)) shouldBe Queen(true)
      cb.getPiece(Tile('e', 4)) shouldBe Empty()
  "getPiece(Move)" should:
    "return the piece located at move.from" in:
      val cb = Chessboard.initial
      val move = Move(Tile('e', 2), Tile('e', 4))

      cb.getPiece(move) shouldBe Pawn(false)
    // "throw an exception for invalid tiles" in:
    //   val chessboard = Chessboard.initial
    //   val move = Move(Tile('z', 9), Tile('e', 4))

    //   an[NoSuchElementException] shouldBe thrownBy:
    //     chessboard.getPiece(move)
  "setPiece(Tile, Piecef)" should:
    val cb = Chessboard.initial
    val piece = Pawn(false)
    val tile = Tile('a', 3)
    "return a chessboard with pawn on a3" in:
      val newCb = cb.setPiece(tile, piece)
      newCb.getPiece(tile) shouldBe Pawn(false)
  "move(Move)" should:
    val cb = Chessboard.initial
    val tile1 = Tile('a', 2)
    val tile2 = Tile('a', 3)
    val move = Move(tile1, tile2)
    "return a new chessboard with implemented move" in:
      val newCb = cb.move(move)
      newCb.getPiece(tile2) shouldBe Pawn(false)
  "override toString()" should:
    "print chessboard" in:
      val cb = Chessboard.initial
      cb.toString shouldBe """  + - + - + - + - + - + - + - + - +
8 | ♜ | ♞ | ♝ | ♛ | ♚ | ♝ | ♞ | ♜ |
  + - + - + - + - + - + - + - + - +
7 | ♟ | ♟ | ♟ | ♟ | ♟ | ♟ | ♟ | ♟ |
  + - + - + - + - + - + - + - + - +
6 |   |   |   |   |   |   |   |   |
  + - + - + - + - + - + - + - + - +
5 |   |   |   |   |   |   |   |   |
  + - + - + - + - + - + - + - + - +
4 |   |   |   |   |   |   |   |   |
  + - + - + - + - + - + - + - + - +
3 |   |   |   |   |   |   |   |   |
  + - + - + - + - + - + - + - + - +
2 | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ |
  + - + - + - + - + - + - + - + - +
1 | ♖ | ♘ | ♗ | ♕ | ♔ | ♗ | ♘ | ♖ |
  + - + - + - + - + - + - + - + - +
    a   b   c   d   e   f   g   h"""
