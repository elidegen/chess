package model.dataComponent.dataMockImpl

import model.dataComponent.{DataInterface}
import model.dataComponent.dataBaseImpl.{Piece}

/**
 * Mock-Implementierung des Chessboards. Keine Regeln, keine Validierung.
 */
case class MockChessboard(
    rows: Int = 8,
    cols: Int = 8,
    private val board: Map[(Int, Int), Piece] = Map.empty)
    extends DataInterface {

  override def pieceAt(row: Int, col: Int): Option[Piece] =
    board.get((row, col))

  override def setPiece(row: Int, col: Int, piece: Option[Piece]): DataInterface =
    piece match {
      case Some(p) => copy(board = board + ((row, col) -> p))
      case None => copy(board = board - ((row, col)))
    }

  override def clear(): DataInterface =
    copy(board = Map.empty)
}
