package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.*

class ControllerSpec extends AnyWordSpec with Matchers:

  private def freshController(): Controller =
    Controller(GameContext(board = Classic(), currentPlayer = White, state = PlayingState))

  "parseMove(String)" should:

    "apply a valid move to the chessboard" in:
      val controller = freshController()

      controller.parseMove("a2a3")

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Empty()
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Pawn(White)

    "ignore input with wrong length (should not change the board)" in:
      val controller = freshController()
      val beforeA2 = controller.chessboard.getPiece(Tile('a', 2))

      controller.parseMove("a2a3a") // length 5

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe beforeA2
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Empty()

    "ignore input with invalid coordinates (should not change the board)" in:
      val controller = freshController()
      val beforeA2 = controller.chessboard.getPiece(Tile('a', 2))

      controller.parseMove("i2a3") // invalid file

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe beforeA2
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Empty()

    "ignore an illegal move according to rules (should not change the board)" in:
      val controller = freshController()

      controller.parseMove("a2a5")

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      controller.chessboard.getPiece(Tile('a', 5)) shouldBe Empty()

  "newGame" should:

    "reset the game state to a fresh game (classic)" in:
      val controller = freshController()

      controller.parseMove("a2a3")
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Empty()

      controller.newGame(GameMode.Classic)

      controller.ctx.currentPlayer shouldBe White
      controller.ctx.state shouldBe PlayingState
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Empty()

  "undo/redo" should:

    "undo a previously applied valid move" in:
      val controller = freshController()

      controller.parseMove("a2a3")
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Empty()
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Pawn(White)

      controller.undo()

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Empty()

    "redo a previously undone move" in:
      val controller = freshController()

      controller.parseMove("a2a3")
      controller.undo()
      controller.redo()

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Empty()
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Pawn(White)

    "not record invalid moves in the undo/redo history" in:
      val controller = freshController()

      controller.parseMove("a2a5")

      controller.undo()

      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(White)
      controller.chessboard.getPiece(Tile('a', 5)) shouldBe Empty()
