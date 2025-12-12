package controller

import model.{Chessboard, Tile, Move, Classic, Piece, Empty}
import util.{Command, UndoManager}

class MoveCommand(from: Tile, to: Tile, piece: Piece, capturedPiece: Option[Piece], chessboard: Chessboard) extends Command:

  val previousPiece: Piece = chessboard.getPiece(from)
  val previousCapturedPiece: Option[Piece] = capturedPiece

  override def doStep: Unit =
    chessboard.setPiece(to, piece)  // Schachstück auf Ziel setzen
    chessboard.setPiece(from, Empty())  // Start-Feld leeren
    capturedPiece.foreach(captured =>
      chessboard.setPiece(to, captured))  // Falls es ein geschlagenes Stück gibt, setzen wir es auf das Ziel
  override def undoStep: Unit =
    chessboard.setPiece(from, previousPiece)  // Ursprüngliches Schachstück zurücksetzen
    chessboard.setPiece(to, Empty())  // Ziel-Feld leeren
    previousCapturedPiece.foreach(captured =>
      chessboard.setPiece(from, captured))  // Falls es ein geschlagenes Stück gab, zurücksetzen

  override def redoStep: Unit = doStep
