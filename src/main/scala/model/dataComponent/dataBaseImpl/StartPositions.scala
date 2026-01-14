package model.dataComponent.dataBaseImpl

object StartPositions:
  private def setClassicPosition(x: Char, y: Int): Piece =
    (x, y) match
      case (_, 2) => Pawn(White)
      case (_, 7) => Pawn(Black)
      case ('a' | 'h', 1) => Rook(White)
      case ('a' | 'h', 8) => Rook(Black)
      case ('b' | 'g', 1) => Knight(White)
      case ('b' | 'g', 8) => Knight(Black)
      case ('c' | 'f', 1) => Bishop(White)
      case ('c' | 'f', 8) => Bishop(Black)
      case ('d', 1) => Queen(White)
      case ('d', 8) => Queen(Black)
      case ('e', 1) => King(White)
      case ('e', 8) => King(Black)
      case _ => Empty()

  val classic: Map[Tile, Piece] =
    (
      for
        x <- 'a' to 'h'
        y <- 1 to 8
      yield Tile(x, y) -> setClassicPosition(x, y)
    ).toMap
