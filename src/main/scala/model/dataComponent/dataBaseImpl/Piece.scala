package model.dataComponent.dataBaseImpl

sealed trait Piece:
  override def toString(): String

case class Pawn(color: Color) extends Piece:
  override def toString(): String = if (color == Black) "♟" else "♙"

case class Rook(color: Color) extends Piece:
  override def toString(): String = if (color == Black) "♜" else "♖"

case class Knight(color: Color) extends Piece:
  override def toString(): String = if (color == Black) "♞" else "♘"

case class Bishop(color: Color) extends Piece:
  override def toString(): String = if (color == Black) "♝" else "♗"

case class King(color: Color) extends Piece:
  override def toString(): String = if (color == Black) "♚" else "♔"

case class Queen(color: Color) extends Piece:
  override def toString(): String = if (color == Black) "♛" else "♕"

case class Empty() extends Piece:
  override def toString(): String = " "
