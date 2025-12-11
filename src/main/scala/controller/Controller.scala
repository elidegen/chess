package controller

import model.{Chessboard, Tile, Move}
import util.{Observable,UndoManager}

case class Controller(var cb: Chessboard) extends Observable:
  var chessboard = cb;
  var mode: String = "classic"

  def setMode(newMode: String): Unit =
    mode = newMode
    chessboard = Chessboard.apply(mode)

  def set(row: Int, col: Int, value: Int): Unit =
    undoManager.doStep(new SetCommand())
    notifyObservers

  def undo(): Unit =
    undoManager.undoStep()
    chessboard = undoManager.undoStack.head.doStep()

  def redo(): Unit =
    undoManager.redoStep()
    chessboard = undoManager.redoStack.head.doStep()

  def parseMove(input: String): Unit =
    val move = input.replace(" ", "")
    val fromX = move(0)
    val fromY = move(1).asDigit
    val toX = move(2)
    val toY = move(3).asDigit

    if (move.length != 4)
      println("ungueltiger move: beispielmove: e2e4")
      return

    if (fromX < 'a' || fromX > 'h' || toX < 'a' || toX > 'h')
      println("ungueltiger move: X muss zwischen a-h liegen")
      return

    if (fromY < 1 || fromY > 8 || toY < 1 || toY > 8)
      println("ungueltiger move: Y muss zwischen 1 - 8 liegen")
      return

    chessboard = chessboard.move(Move(Tile(fromX, fromY), Tile(toX, toY)))
    notifyObservers
