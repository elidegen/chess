package controller

import util.Observable

class Controller() extends Observable:
  var chessboard: Chessboard = Chessboard.initial

  def newGame(): Unit =
    chessboard = Chessboard.initial
