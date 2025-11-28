package model

sealed trait Piece:
  def isMoveLegal(): Boolean
  override def toString(): String

case class Pawn(black: Boolean) extends Piece:
  override def isMoveLegal(): Boolean =
    //Logic hier
    true
  override def toString(): String = if (black) "♟" else "♙"

case class Rook(black: Boolean) extends Piece:
  override def isMoveLegal(): Boolean =
    //Logic hier
    true
  override def toString(): String = if (black) "♜" else "♖"

case class Knight(black: Boolean) extends Piece:
  override def isMoveLegal(): Boolean =
    // Logic hier
    true
  override def toString(): String = if (black) "♞" else "♘"

case class Bishop(black: Boolean) extends Piece:
  override def isMoveLegal(): Boolean =
    // Logic hier
    true
  override def toString(): String = if (black) "♝" else "♗"

case class King(black: Boolean) extends Piece:
  override def isMoveLegal(): Boolean =
    // Logic here
    true
  override def toString(): String = if (black) "♚" else "♔"

case class Queen(black: Boolean) extends Piece:
  override def isMoveLegal(): Boolean =
    // Logic hier
    true
  override def toString(): String = if (black) "♛" else "♕"

case class Empty() extends Piece:
  override def isMoveLegal(): Boolean = false
  override def toString(): String = " "
