package model

import model._

class Chessboard(tiles: Map[Tile, Piece]):

  protected def createInitialTiles(): Map[Tile, Piece]

  protected def newBoard(tiles: Map[Tile, Piece]): Chessboard

  protected def createMoveValidator(): MoveValidator

  lazy val validator: MoveValidator = createMoveValidator()

  def validateMove(move: Move): Boolean =
    validator.validate(move, this)

  def reset(): Chessboard =
    newBoard(createInitialTiles())

  def getPiece(move: Move): Piece = tiles.getOrElse(move.from, Empty())

  def getPiece(tile: Tile): Piece = tiles.getOrElse(tile, Empty())

  def setPiece(tile: Tile, piece: Piece): Chessboard = newBoard(tiles = tiles + (tile -> piece))

  def move(move: Move): Chessboard =
    val piece = getPiece(move)
    // setPiece(move.to, piece).setPiece(move.from, Empty())
    newBoard(tiles + (move.to -> piece) + (move.from -> Empty()))

  override def toString(): String =
    val size = 8
    var board =
      ("  +" + " - +" * size + "\n" + "q " + "| x " * size + "|\n") * (size) + "  +" + " - +" * size + "\n    r   r   r   r   r   r   r   r"
    for
      y <- size to 1 by -1
      x <- 'a' to 'h'
    do
      board = board
        .replaceFirst("x", getPiece(Tile(x, y)).toString())
        .replaceFirst("r", x.toString)
    for y <- size to 1 by -1
    do board = board.replaceFirst("q", y.toString)
    board

object Chessboard:

  def apply(mode: String = "Classic"): Chessboard =
    Chessboard(initialTiles(mode))

  private def initialTiles(mode: String): Map[Tile, Piece] =
    (
      for
        x <- 'a' to 'h'
        y <- 1 to 8
      yield Tile(x, y) -> createPieceCustom(x, y, mode)
    ).toMap
