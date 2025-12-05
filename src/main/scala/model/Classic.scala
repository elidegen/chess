import model.{Chessboard, Tile, Piece, Pawn, Rook, Knight, Bishop, Queen, King, Empty, Move, MoveValidator}

class Classic(initialTiles: Map[Tile, Piece]) extends Chessboard(initialTiles):

  override def validateMove(move: Move): Boolean = ???

  override protected def newBoard(tiles: Map[Tile, Piece]): Chessboard =
    Classic(tiles)

  override protected def createInitialTiles(): Map[Tile, Any] = ???

  override protected def createMoveValidator(): MoveValidator =
    ClassicMoveValidator()

  private def initiateTiles: Map[Tile, Piece] =
    (
      for
        x <- 'a' to 'h'
        y <- 1 to 8
      yield Tile(x, y) -> this.setInitialPiece(x, y)
    ).toMap

  private def setInitialPiece(x: Char, y: Int): Piece =
    (x, y) match
      case (_, 2) => Pawn(false)
      case (_, 7) => Pawn(true)
      case ('a' | 'h', 1) => Rook(false)
      case ('a' | 'h', 8) => Rook(true)
      case ('b' | 'g', 1) => Knight(false)
      case ('b' | 'g', 8) => Knight(true)
      case ('c' | 'f', 1) => Bishop(false)
      case ('c' | 'f', 8) => Bishop(true)
      case ('d', 1) => Queen(false)
      case ('d', 8) => Queen(true)
      case ('e', 1) => King(false)
      case ('e', 8) => King(true)
      case _ => Empty()

case class ClassicMoveValidator() extends MoveValidator:
  override def validate(move: Move, cb: Chessboard): Boolean =
    move match
      case getPiece(move) isInstanceOf[Empty] =>
