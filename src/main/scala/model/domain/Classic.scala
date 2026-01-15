package model.domain

import model.domain.*

class Classic(initialTiles: Map[Tile, Piece]) extends Chessboard(initialTiles):

  override protected def newBoard(tiles: Map[Tile, Piece]): Chessboard =
    new Classic(tiles)

  override protected def createInitialTiles(): Map[Tile, Piece] =
    StartPositions.classic

  override protected def createMoveValidator(): MoveValidator =
    ClassicMoveValidator()

object Classic:
  def apply(): Classic =
    new Classic(StartPositions.classic)
