package controller

import model.{Chessboard, Tile, Move}
import util.Observable

case class Controller() extends Observable:
  var chessboard: Chessboard = Chessboard.initial

  def newGame: Unit =
    chessboard = Chessboard.initial
    notifyObservers

  def parseMove(input: String): Move =
    val move = input.replace(" ", "")
    val fromX = move(0)
    val fromY = move(1).asDigit
    val toX = move(2)
    val toY = move(3).asDigit
    Move(Tile(fromX, fromY), Tile(toX, toY))
