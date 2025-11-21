package controller

import model.{Chessboard, Tile, Move}
import util.Observable

case class Controller(cb: Chessboard) extends Observable:
  var chessboard = cb;

  def newGame: Unit =
    chessboard = Chessboard.initial
    notifyObservers

  def parseMove(input: String): Unit =
    val move = input.replace(" ", "")
    val fromX = move(0)
    val fromY = move(1).asDigit
    val toX = move(2)
    val toY = move(3).asDigit
    chessboard = chessboard.move(Move(Tile(fromX, fromY), Tile(toX, toY)))
    notifyObservers
