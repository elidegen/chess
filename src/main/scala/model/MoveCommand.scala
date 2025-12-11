package model

import util.Command

case class MoveCommand(from: Tile, to: Tile, piece: Piece, capturedPiece: Option[Piece], chessboard: Chessboard) extends Command

  override def doStep(): Chessboard =
    val newBoard = chessboard.move(Move(from, to))

    capturedPiece.foreach(captured =>
      newBoard.removePiece(captured))
    newBoard


  override def undoStep(): Chessboard =
    val newBoard = chessboard.move(Move(to, from))
    capturedPiece.foreach(captured =>
      newBoard.addPiece(captured, to))
    newBoard

  override def redoStep(): Chessboard = doStep()
