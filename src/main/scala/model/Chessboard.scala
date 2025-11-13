package model

import model.{Tile, Piece, Move}

case class Chessboard(tiles: Map[Tile, Piece]):

  def getPiece(move: Move): Piece = tiles.get(move.from).get: Piece

  def getPiece(tile: Tile): Piece = tiles.getOrElse(tile, Empty())

  def setPiece(tile: Tile, piece: Piece): Chessboard = copy(tiles = tiles + (tile -> piece))

  def isEmpty(piece: Piece): Boolean = piece.isInstanceOf[Empty]

  def move(move: Move): Chessboard =
    val piece = getPiece(move)
    setPiece(move.to, piece).setPiece(move.from, Empty())

  // def getSize(input: () => String = readLine): Int =
  //   println("Spielbrettgröße in Int: ")
  //   input().toIntOption.getOrElse(getSize(input))

  override def toString(): String =
    val size = 8
    var board =
      ("+" + " - +" * size + "\n" + "| x " * size + "|\n") * (size) + "+" + " - +" * size
    for
      x <- 'a' to 'h'
      y <- 1 to size
    yield board = board.replaceFirst("x", getPiece(Tile(x, y)).toString())
    board

object Chessboard:
  def initial: Chessboard =
    Chessboard(initialTiles)

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
