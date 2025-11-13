package aview

import controller.Controller
import util.Observer

class Tui(controller: Controller) extends Observer:
  controller.add(this)
  val size = 8

  def processInput(input: String): Unit =
    input match
      case "q" =>
      case "n" => controller.newGame
      case _ => controller.chessboard.move(controller.parseMove(input))

  override def update: Unit = println(controller.chessboard.toString)
