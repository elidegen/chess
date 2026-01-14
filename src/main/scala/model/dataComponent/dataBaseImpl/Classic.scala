package model.dataComponent.dataBaseImpl

import model.rulesComponent.rulesBaseImpl.{MoveValidator, StrategyProvider, ClassicStrategyProvider, GameContext}

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

case class ClassicMoveValidator(strategies: StrategyProvider = ClassicStrategyProvider)
    extends MoveValidator:
  override protected def isCorrectPlayer(ctx: GameContext, piece: Piece): Boolean =
    val pieceColor = piece match
      case Pawn(c) => c
      case Rook(c) => c
      case Knight(c) => c
      case Bishop(c) => c
      case Queen(c) => c
      case King(c) => c
      case _: Empty => return false
    pieceColor == ctx.currentPlayer

  override def isCheck(board: Chessboard, color: Color): Boolean = false
  override def isDraw(board: Chessboard, color: Color): Boolean = false
  override def isCheckmate(board: Chessboard, color: Color): Boolean = false
