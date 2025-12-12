package model

import util.Command

case class MoveCommand(
    from: Tile,
    to: Tile,
    piece: Piece,
    capturedPiece: Option[Piece],
    chessboard: Chessboard)
    extends Command:

  val previousPiece: Piece = chessboard.getPiece(from)

  override def doStep: Unit =
    chessboard.setPiece(to, piece)
    chessboard.setPiece(from, Empty())
    capturedPiece.foreach(captured => chessboard.setPiece(to, captured))

  override def undoStep: Unit =
    chessboard.setPiece(from, piece)
    chessboard.setPiece(to, Empty())
    capturedPiece.foreach(captured => chessboard.setPiece(from, captured))

  override def redoStep: Unit = doStep
