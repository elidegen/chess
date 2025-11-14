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
      case _ => controller.parseMove(input)

  override def update: Unit =
    print("\u001b[2J\u001b[H")
    println(controller.chessboard.toString)
