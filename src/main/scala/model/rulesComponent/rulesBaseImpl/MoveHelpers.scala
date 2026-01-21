package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState}

object MoveHelpers:
  def charToNr(t: Tile): Int = t.x - 'a'
  def digitToNr(t: Tile): Int = t.y - 1

  def deltaX(move: Move): Int =
    charToNr(move.to) - charToNr(move.from)

  def deltaY(move: Move): Int =
    digitToNr(move.to) - digitToNr(move.from)

  def sign(n: Int): Int = if n == 0 then 0 else if n > 0 then 1 else -1

  def abs(n: Int): Int = if n < 0 then -n else n

  def isEmptyAt(ctx: GameContext, tile: Tile): Boolean =
    ctx.board.getPiece(tile) match
      case _: Empty => true
      case _ => false

  def pieceColor(piece: Piece): Color =
    piece match
      case Pawn(c) => c
      case Rook(c) => c
      case Knight(c) => c
      case Bishop(c) => c
      case Queen(c) => c
      case King(c) => c
      case _: Empty => throw new IllegalArgumentException("Empty has no color")

  def isEnemyAt(ctx: GameContext, tile: Tile, myColor: Color): Boolean =
    ctx.board.getPiece(tile) match
      case _: Empty => false
      case p => pieceColor(p) != myColor

  def isOwnPieceAt(ctx: GameContext, tile: Tile, myColor: Color): Boolean =
    ctx.board.getPiece(tile) match
      case _: Empty => false
      case p => pieceColor(p) == myColor

  def intermediateTilesOnLine(from: Tile, to: Tile): List[Tile] =
    val fromX = charToNr(from); val fromY = digitToNr(from) // b1 -> (1,0)
    val toX = charToNr(to); val toY = digitToNr(to) // h8 -> (7,7)

    val stepX = sign(toX - fromX)
    val stepY = sign(toY - fromY)

    if (stepX == 0 && stepY == 0) then Nil
    else if (stepX != 0 && stepY != 0 && abs(toX - fromX) != abs(toY - fromY)) then Nil
    else if (stepX != 0 && stepY == 0 || stepX == 0 && stepY != 0 || abs(toX - fromX) == abs(
          toY - fromY))
    then
      val len = Math.max(abs(toX - fromX), abs(toY - fromY))
      (1 until len).toList.map { i =>
        val x = (fromX + i * stepX + 'a').toChar
        val y = fromY + i * stepY + 1
        Tile(x, y)
      }
    else Nil

  def isPathClear(ctx: GameContext, from: Tile, to: Tile): Boolean =
    intermediateTilesOnLine(from, to).forall(t => isEmptyAt(ctx, t))
