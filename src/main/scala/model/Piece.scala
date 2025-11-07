
sealed trait Piece(black: Boolean) extends Tile:
    def move(targetx: Char, targety: Int): Tile
