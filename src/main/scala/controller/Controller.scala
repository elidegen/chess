package controller

import model.{Chessboard, Tile, Move}
import util.Observable

case class Controller(var cb: Chessboard) extends Observable:
  var chessboard = cb;
  var mode: String = "classic"

  def newGame: Unit =
    chessboard = Chessboard.initial(mode)
    notifyObservers

  def setMode(newMode: String): Unit =
    mode = newMode
    chessboard = Chessboard.initial(mode)
    notifyObservers

  def parseMove(input: String): Unit =
    val move = input.replace(" ", "")
    val fromX = move(0)
    val fromY = move(1).asDigit
    val toX = move(2)
    val toY = move(3).asDigit
    chessboard = chessboard.move(Move(Tile(fromX, fromY), Tile(toX, toY)), mode)
    notifyObservers
