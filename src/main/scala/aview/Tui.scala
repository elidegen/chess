package aview

import controller.Controller

class Tui(controller: Controller) extends Observer:
  controller.add(this)
  val size = 8

  def processInput(input: String): Unit =
    input match
      case "q" =>
      case "n" => controller.newGame
      case _ =>
