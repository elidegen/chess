package model

sealed trait Piece:
  def isMoveLegal(ctx: GameContext, move: Move): Boolean
  override def toString(): String

case class Pawn(color: Color) extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean =
    // Logic hier
    true
  override def toString(): String = if (color == Black) "♟" else "♙"

case class Rook(color: Color) extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean =
    // Logic hier
    true
  override def toString(): String = if (color == Black) "♜" else "♖"

case class Knight(color: Color) extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean =
    // Logic hier
    true
  override def toString(): String = if (color == Black) "♞" else "♘"

case class Bishop(color: Color) extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean =
    // Logic hier
    true
  override def toString(): String = if (color == Black) "♝" else "♗"

case class King(color: Color) extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean =
    // Logic here
    true
  override def toString(): String = if (color == Black) "♚" else "♔"

case class Queen(color: Color) extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean =
    // Logic hier
    true
  override def toString(): String = if (color == Black) "♛" else "♕"

case class Empty() extends Piece:
  override def isMoveLegal(ctx: GameContext, move: Move): Boolean = false
  override def toString(): String = " "
