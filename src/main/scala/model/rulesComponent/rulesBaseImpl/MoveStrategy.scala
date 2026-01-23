package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState}

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

trait MoveStrategy:
  def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean

object RookMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = MoveHelpers.pieceColor(piece)
    val dx = MoveHelpers.deltaX(move)
    val dy = MoveHelpers.deltaY(move)

    val isStraight = (dx == 0 && dy != 0) || (dx != 0 && dy == 0)
    if !isStraight then false
    else if !MoveHelpers.isPathClear(ctx, move.from, move.to) then false
    else if MoveHelpers.isOwnPieceAt(ctx, move.to, color) then false
    else true

object BishopMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = MoveHelpers.pieceColor(piece)
    val dx = MoveHelpers.abs(MoveHelpers.deltaX(move))
    val dy = MoveHelpers.abs(MoveHelpers.deltaY(move))

    if dx == 0 || dy == 0 || dx != dy then false
    else if !MoveHelpers.isPathClear(ctx, move.from, move.to) then false
    else if MoveHelpers.isOwnPieceAt(ctx, move.to, color) then false
    else true

object QueenMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = MoveHelpers.pieceColor(piece)
    val dx = MoveHelpers.deltaX(move)
    val dy = MoveHelpers.deltaY(move)

    val rookLike = (dx == 0 && dy != 0) || (dx != 0 && dy == 0)
    val bishopLike = MoveHelpers.abs(dx) == MoveHelpers.abs(dy) && dx != 0

    if !(rookLike || bishopLike) then false
    else if !MoveHelpers.isPathClear(ctx, move.from, move.to) then false
    else if MoveHelpers.isOwnPieceAt(ctx, move.to, color) then false
    else true

object KnightMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = MoveHelpers.pieceColor(piece)
    val dx = MoveHelpers.abs(MoveHelpers.deltaX(move))
    val dy = MoveHelpers.abs(MoveHelpers.deltaY(move))

    val isL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2)
    if !isL then false
    else if MoveHelpers.isOwnPieceAt(ctx, move.to, color) then false
    else true

object KingMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = MoveHelpers.pieceColor(piece)
    val dx = MoveHelpers.abs(MoveHelpers.deltaX(move))
    val dy = MoveHelpers.abs(MoveHelpers.deltaY(move))

    val isOneStep = dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0)
    if !isOneStep then false
    else if MoveHelpers.isOwnPieceAt(ctx, move.to, color) then false
    else true

object PawnMoveStrategy extends MoveStrategy:

  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean =
    val color = MoveHelpers.pieceColor(piece)

    val dx = MoveHelpers.deltaX(move)
    val dy = MoveHelpers.deltaY(move)

    val forward = if color == White then 1 else -1
    val startRank = if color == White then 2 else 7

    val toEmpty = MoveHelpers.isEmptyAt(ctx, move.to)

    val oneStep =
      dx == 0 && dy == forward && toEmpty

    val twoStep =
      dx == 0 &&
        dy == 2 * forward &&
        move.from.y == startRank &&
        toEmpty &&
        MoveHelpers.isEmptyAt(ctx, Tile(move.from.x, move.from.y + forward))

    val capture =
      MoveHelpers.abs(dx) == 1 &&
        dy == forward &&
        !toEmpty &&
        MoveHelpers.isEnemyAt(ctx, move.to, color)

    oneStep || twoStep || capture

object EmptyMoveStrategy extends MoveStrategy:
  override def isLegal(ctx: GameContext, move: Move, piece: Piece): Boolean = false
