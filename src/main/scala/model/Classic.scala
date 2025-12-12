package model

class Classic(initialTiles: Map[Tile, Piece]) extends Chessboard(initialTiles):

  override protected def newBoard(tiles: Map[Tile, Piece]): Chessboard =
    new Classic(StartPositions.classic)

  override protected def createInitialTiles(): Map[Tile, Piece] =
    StartPositions.classic

  override protected def createMoveValidator(): MoveValidator =
    ClassicMoveValidator()

object Classic:
  def apply(): Classic =
    new Classic(StartPositions.classic)

case class ClassicMoveValidator() extends MoveValidator:
  override def validate(ctx: GameContext, move: Move): Boolean =
    ctx.board.getPiece(move) match
      case _: Empty => false
      case piece =>
        val pieceColor = piece match
          case Pawn(c) => c
          case Rook(c) => c
          case Knight(c) => c
          case Bishop(c) => c
          case Queen(c) => c
          case King(c) => c

        if pieceColor != ctx.currentPlayer then false
        else if !piece.isMoveLegal(ctx, move) then false
        else if ctx.board.validator.isCheck(ctx.board, ctx.currentPlayer) then false
        else true
