package controller

import model.{Chessboard, Tile, Move, Classic, StartPositions, Piece}
import util.{Observable, UndoManager}

case class Controller(var chessboard: Chessboard) extends Observable:

  val undoManager = new UndoManager()

  //def set(from: Tile, to: Tile, piece: Piece): Unit =
  //  val piece = chessboard.getPiece(tile)
  //  undoManager.doStep(new MoveCommand(tile, piece, this))
  //  notifyObservers

  def movePiece(from: Tile, to: Tile, piece: Piece, capturedPiece: Option[Piece]): Unit =
    val moveCommand = new MoveCommand(from, to, piece, capturedPiece, chessboard)
    moveCommand.doStep
    notifyObservers


  def undo: Unit =
    undoManager.undoStep
    notifyObservers

  def redo: Unit =
    undoManager.redoStep
    notifyObservers

  def newGame =
    chessboard = new Classic(StartPositions.classic)
    notifyObservers

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
