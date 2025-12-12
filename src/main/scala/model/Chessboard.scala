package model

import model._

abstract class Chessboard(tiles: Map[Tile, Piece]):

  protected def createInitialTiles(): Map[Tile, Piece]

  protected def newBoard(tiles: Map[Tile, Piece]): Chessboard

  protected def createMoveValidator(): MoveValidator

  lazy val validator: MoveValidator = createMoveValidator()

  def validateMove(ctx: GameContext, move: Move): Boolean =
    validator.validate(ctx, move)

  def reset(): Chessboard =
    newBoard(createInitialTiles())

  def getPiece(move: Move): Piece = tiles.getOrElse(move.from, Empty())

  def getPiece(tile: Tile): Piece = tiles.getOrElse(tile, Empty())

  def setPiece(tile: Tile, piece: Piece): Chessboard =
    newBoard(tiles + (tile -> piece))

  def move(move: Move): Chessboard =
    val piece = getPiece(move)
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
