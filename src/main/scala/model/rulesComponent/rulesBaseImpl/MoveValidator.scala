package model.rulesComponent.rulesBaseImpl

import model.dataComponent.dataBaseImpl.{Color, Move, Piece, Empty, Chessboard}

// color is the player that made the last move
abstract class MoveValidator():
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
