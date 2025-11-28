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
      case "q" =>
      case "n" => setMode()
      case _ => controller.parseMove(input)

  def setMode(): Unit =
    val mode = scala.io.StdIn.readLine("Choose a mode:\n" +
        "1. classic\n").trim match
      case "classic" => "classic"
      //case "Chess960" => "Chess960"
      case _   => "classic"

    controller.setMode(mode)

  override def update: Unit =
    print("\u001b[2J\u001b[H")
    println(controller.chessboard.toString)
