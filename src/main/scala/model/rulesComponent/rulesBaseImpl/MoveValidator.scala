package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}

trait MoveValidator() extends MoveValidatorInterface:
  final def validate(ctx: GameContext, move: Move): Boolean =
    val piece = ctx.board.getPiece(move)

    if isEmpty(piece) then false
    else if !isCorrectPlayer(ctx, piece) then false
    else if !isPieceMoveLegal(ctx, piece, move) then false
    else if wouldLeaveKingInCheck(ctx, move) then false
    else if !passSpecificRules(ctx, piece, move) then false
    else true

  protected def isEmpty(piece: Piece): Boolean = piece.isInstanceOf[Empty]

  protected def isCorrectPlayer(ctx: GameContext, piece: Piece): Boolean

  protected def strategies: StrategyProvider

  protected def isPieceMoveLegal(ctx: GameContext, piece: Piece, move: Move): Boolean =
    val strat = strategies.strategyFor(piece)
    strat.isLegal(ctx, move, piece);

  protected def wouldLeaveKingInCheck(ctx: GameContext, move: Move): Boolean =
    val boardAfter = ctx.board.move(move)
    isCheck(boardAfter, ctx.currentPlayer)

  protected def passSpecificRules(ctx: GameContext, piece: Piece, move: Move): Boolean = true

  def isCheck(board: Chessboard, color: Color): Boolean
  def isDraw(board: Chessboard, color: Color): Boolean
  def isCheckmate(board: Chessboard, color: Color): Boolean

final case class ClassicMoveValidator(override protected val strategies: StrategyProvider = ClassicStrategyProvider) extends MoveValidator:
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

  def isCheck(board: Chessboard, color: Color): Boolean = false;
  def isDraw(board: Chessboard, color: Color): Boolean = false;
  def isCheckmate(board: Chessboard, color: Color): Boolean = false;
