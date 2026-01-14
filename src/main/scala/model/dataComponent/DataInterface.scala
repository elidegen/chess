package model.dataComponent

import model.dataComponent.dataBaseImpl.{Piece}

trait DataInterface {

  def rows: Int
  def cols: Int

  def pieceAt(row: Int, col: Int): Option[Piece]

  def setPiece(row: Int, col: Int, piece: Option[Piece]): DataInterface

  def clear(): DataInterface
}
