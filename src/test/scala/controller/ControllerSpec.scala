package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model._

class ControllerSpec extends AnyWordSpec with Matchers:
  "newGame" should:
    "reset chessboard to start position" in:
      val controller = Controller()
      controller.chessboard.move(Move(Tile('a', 2), Tile('a', 3)))
      controller.newGame
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(false)
  "parseMove(String)" should:
    "apply move to chessboard" in:
      val controller = Controller()
      controller.parseMove("a2a3")
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Empty()
