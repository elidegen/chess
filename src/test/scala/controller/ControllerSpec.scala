package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model._

class ControllerSpec extends AnyWordSpec with Matchers:
  "setMode" should:
    "start a new game in proper mode" in:
      val controller = Controller(Chessboard.apply("classic"))
      controller.chessboard.move(Move(Tile('a', 2), Tile('a', 3)))
      controller.setMode("classic")
      controller.chessboard.getPiece(Tile('a', 1)) shouldBe Rook(false)
  "parseMove(String)" should:
    "apply move to chessboard" in:
      val controller = Controller(Chessboard.apply("classic"))
      controller.parseMove("a2a3")
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Empty()
