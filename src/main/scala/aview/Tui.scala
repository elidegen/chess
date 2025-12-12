package aview

import controller.Controller
import util.Observer

class Tui(controller: Controller) extends Observer:
  controller.add(this)
  val size = 8

  def processInput(input: String): Unit =
    val trimmedInput = input.trim

    if (trimmedInput.isEmpty)
      println("Eingabe darf nicht leer sein")
      return

    trimmedInput match
      case "quit" =>
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "new" => controller.newGame()
      case _ => controller.parseMove(input)

  override def update: Unit =
    print("\u001b[2J\u001b[H")
    println(controller.chessboard.toString)
