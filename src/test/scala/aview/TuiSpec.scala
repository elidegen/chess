package aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import controller._
import model._

class TuiSpec extends AnyWordSpec with Matchers:
  "processInput(String)" should:
    "create new chessboard" in:
      val controller = Controller(Chessboard.initial("classic"))
      val tui = Tui(controller)
      controller.chessboard.move(Move(Tile('a', 2), Tile('a', 3)))
      tui.processInput("n")
      controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(false)
    //"change mode" in:
      //val controller = Controller(Chessboard.initial("classic"))
      //val tui = Tui(controller)
      //controller.chessboard.move(Move(Tile('a', 2), Tile('a', 3)), "classic")
      //tui.processInput("mode")
      //tui.processInput("1")
      //controller.chessboard.getPiece(Tile('a', 2)) shouldBe Pawn(false)
    //"trim and have empty input" in:
      //val controller = Controller(Chessboard.initial("classic"))
      //val tui = Tui(controller)
      //tui.processInput("")
    "make a move" in:
      val controller = Controller(Chessboard.initial("classic"))
      val tui = Tui(controller)
      tui.processInput("a2a3")
      controller.chessboard.getPiece(Tile('a', 3)) shouldBe Pawn(false)
    "do nothing" in:
      val controller = Controller(Chessboard.initial("classic"))
      val tui = Tui(controller)
      val before = controller.chessboard
      tui.processInput("q")
      controller.chessboard shouldBe before
  "add" should:
    "add tui to observer list of controller" in:
      val controller = Controller(Chessboard.initial("classic"))
      val tui = Tui(controller)
      controller.add(tui)
      controller.subscribers.length shouldBe 2
  "remove" should:
    "add tui to observer list of controller" in:
      val controller = Controller(Chessboard.initial("classic"))
      val tui = Tui(controller)
      controller.add(tui)
      controller.remove(tui)
      controller.subscribers.length shouldBe 0
  "setMode" should:
    "start a new game in proper mode" in:
      val controller = Controller(Chessboard.initial("classic"))
      val tui = Tui(controller)
      controller.add(tui)
      controller.setMode("classic")