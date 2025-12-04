package Model

trait MoveStrategy:
  def isLegalMove(): Boolean

class PawnMoveStrategy extends MoveStrategy:
  def isLegalMove(): Boolean =
    // logik
    true

class RookMoveStrategy extends MoveStrategy:
  def isLegalMove(): Boolean =
    // logik
    true

class KnightMoveStrategy extends MoveStrategy:
  def isLegalMove(): Boolean =
    // logik
    true

class BishopMoveStrategy extends MoveStrategy:
  def isLegalMove(): Boolean =
    // logik
    true

class QueenMoveStrategy extends MoveStrategy:
  def isLegalMove(): Boolean =
    // logik
    true

class KingMoveStrategy extends MoveStrategy:
  def isLegalMove(): Boolean =
    // logik
    true