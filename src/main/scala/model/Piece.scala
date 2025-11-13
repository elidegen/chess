package model

sealed trait Piece:
  override def toString(): String =
    this match
      case Pawn(true) => "♟"
      case Pawn(false) => "♙"
      case Rook(true) => "♜"
      case Rook(false) => "♖"
      case Knight(true) => "♞"
      case Knight(false) => "♘"
      case Bishop(true) => "♝"
      case Bishop(false) => "♗"
      case Queen(true) => "♛"
      case Queen(false) => "♕"
      case King(true) => "♚"
      case King(false) => "♔"
      case Empty() => " "

case class Pawn(black: Boolean) extends Piece
case class Rook(black: Boolean) extends Piece
case class Knight(black: Boolean) extends Piece
case class Bishop(black: Boolean) extends Piece
case class King(black: Boolean) extends Piece
case class Queen(black: Boolean) extends Piece

case class Empty() extends Piece
