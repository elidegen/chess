package controller

import model.{Tile, Piece}
import util.Command

class SetCommand(tile: Tile, piece: Piece, controller: Controller) extends Command:

  val previousPiece: Piece = controller.ctx.board.getPiece(tile)

  override def doStep: Unit = controller.ctx =
    controller.ctx.copy(board = controller.ctx.board.setPiece(tile, piece))

  override def undoStep: Unit = controller.ctx =
    controller.ctx.copy(board = controller.ctx.board.setPiece(tile, previousPiece))

  override def redoStep: Unit = doStep
