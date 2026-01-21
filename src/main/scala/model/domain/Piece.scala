package model.domain

object PieceRender:
  private val useUnicode: Boolean =
    sys.props.get("chess.unicode").forall(_.toLowerCase != "false")

  def symbolOr(letter: Char, unicode: String, color: Color): String =
    if useUnicode then unicode
    else if color == Black then letter.toLower.toString
    else letter.toUpper.toString

sealed trait Piece:
  override def toString(): String

case class Pawn(color: Color) extends Piece:
  override def toString(): String =
    PieceRender.symbolOr('p', if (color == Black) "♟" else "♙", color)

case class Rook(color: Color) extends Piece:
  override def toString(): String =
    PieceRender.symbolOr('r', if (color == Black) "♜" else "♖", color)

case class Knight(color: Color) extends Piece:
  override def toString(): String =
    PieceRender.symbolOr('n', if (color == Black) "♞" else "♘", color)

case class Bishop(color: Color) extends Piece:
  override def toString(): String =
    PieceRender.symbolOr('b', if (color == Black) "♝" else "♗", color)

case class King(color: Color) extends Piece:
  override def toString(): String =
    PieceRender.symbolOr('k', if (color == Black) "♚" else "♔", color)

case class Queen(color: Color) extends Piece:
  override def toString(): String =
    PieceRender.symbolOr('q', if (color == Black) "♛" else "♕", color)

case class Empty() extends Piece:
  override def toString(): String = " "
