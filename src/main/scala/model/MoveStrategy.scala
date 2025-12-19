package model

import MoveHelpers.*

trait MoveStrategy:
  def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean

trait StrategyProvider:
  def strategyFor(piece: Piece): MoveStrategy

object ClassicStrategyProvider extends StrategyProvider:
  def strategyFor(piece: Piece): MoveStrategy =
    piece match
      case _: Pawn => PawnMoveStrategy
      case _: Rook => RookMoveStrategy
      case _: Knight => KnightMoveStrategy
      case _: Bishop => BishopMoveStrategy
      case _: Queen => QueenMoveStrategy
      case _: King => KingMoveStrategy
      case _: Empty => EmptyMoveStrategy

object RookMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = pieceColor(piece)
    val dx = deltaX(move)
    val dy = deltaY(move)

    val isStraight = (dx == 0 && dy != 0) || (dx != 0 && dy == 0)
    if !isStraight then false
    else if !isPathClear(ctx, move.from, move.to) then false
    else if isOwnPieceAt(ctx, move.to, color) then false
    else true

object BishopMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = pieceColor(piece)
    val dx = abs(deltaX(move))
    val dy = abs(deltaY(move))

    if dx == 0 || dy == 0 || dx != dy then false
    else if !isPathClear(ctx, move.from, move.to) then false
    else if isOwnPieceAt(ctx, move.to, color) then false
    else true

object QueenMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = pieceColor(piece)
    val dx = deltaX(move)
    val dy = deltaY(move)

    val rookLike = (dx == 0 && dy != 0) || (dx != 0 && dy == 0)
    val bishopLike = abs(dx) == abs(dy) && dx != 0

    if !(rookLike || bishopLike) then false
    else if !isPathClear(ctx, move.from, move.to) then false
    else if isOwnPieceAt(ctx, move.to, color) then false
    else true

object KnightMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = pieceColor(piece)
    val dx = abs(deltaX(move))
    val dy = abs(deltaY(move))

    val isL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2)
    if !isL then false
    else if isOwnPieceAt(ctx, move.to, color) then false
    else true

object KingMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = pieceColor(piece)
    val dx = abs(deltaX(move))
    val dy = abs(deltaY(move))

    val isOneStep = dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0)
    if !isOneStep then false
    else if isOwnPieceAt(ctx, move.to, color) then false
    else true

object PawnMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = pieceColor(piece)

    val dx = deltaX(move)
    val dy = deltaY(move)

    val forward = if color == White then 1 else -1
    val startRank = if color == White then 2 else 7

    val toEmpty = isEmptyAt(ctx, move.to)

    val oneStep =
      dx == 0 && dy == forward && toEmpty

    val twoStep =
      dx == 0 &&
        dy == 2 * forward &&
        move.from.y == startRank &&
        toEmpty &&
        isEmptyAt(ctx, Tile(move.from.x, move.from.y + forward))

    val capture =
      abs(dx) == 1 &&
        dy == forward &&
        !toEmpty &&
        isEnemyAt(ctx, move.to, color)

    oneStep || twoStep || capture

object EmptyMoveStrategy extends MoveStrategy:
  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean = false
