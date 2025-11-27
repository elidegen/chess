class Classic extends Chessboard
  private def initialTiles: Map[Tile, Piece] =
    (
      for
        x <- 'a' to 'h'
        y <- 1 to 8
      yield
        val piece: Piece = (x, y) match
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

        Tile(x, y) -> piece
    ).toMap

