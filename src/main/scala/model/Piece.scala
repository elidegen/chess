sealed trait Piece:
    override def toString(): String =
        enum [♔, ♕, ♖, ♗, ♘, ♙, ♚, ♛, ♜, ♝, ♞, ♟]

case class Pawn(black: Boolean) extends Piece
case class Rook(black: Boolean) extends Piece
case class Knight(black: Boolean) extends Piece
case class Bishop(black: Boolean) extends Piece
case class King(black: Boolean) extends Piece
case class Queen(black: Boolean) extends Piece

case class Empty() extends Piece
