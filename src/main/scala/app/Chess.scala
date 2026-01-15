package app

import scala.io.StdIn.readLine
import aview.{Tui, Gui}
import controller.controllerComponent.ControllerInterface
import app.ChessModule.controller

object Chess:
  def main(args: Array[String]): Unit =
    val controller = summon[ControllerInterface]
    Gui.start(controller)
    val tui = Tui(controller)
    Iterator
      .continually(Option(readLine()))
      .takeWhile(_.exists(_ != "quit"))
      .flatten
      .foreach(tui.processInput)
