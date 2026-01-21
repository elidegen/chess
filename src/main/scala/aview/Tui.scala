package aview

import controller.controllerComponent.ControllerInterface
import util.Observer

class Tui(controller: ControllerInterface) extends Observer:
  controller.add(this)

  def processInput(input: String): Unit =
    val trimmedInput = input.trim

    if (trimmedInput.isEmpty)
      println("input should not be empty")
      return

    trimmedInput match
      case "quit" => System.exit(0)
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "new" => controller.newGame()
      case _ => controller.parseMove(trimmedInput)

  override def update: Unit =
    print("\u001b[2J\u001b[H")
    println(controller.chessboard.toString)
