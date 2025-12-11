package controller

import model.{Chessboard, Tile, Piece}
//import model._
import util.Command
//import util._

class SetCommand(tile: Tile, piece: Piece, controller: Controller) extends Command

  val previousPiece: Piece =    controller.chessboard.getPiece(tile)

  override def doStep: Unit =   controller.chessboard = controller.chessboard.setPiece(tile, piece)

  override def undoStep: Unit = controller.chessboard = controller.chessboard.setPiece(tile, previousPiece)

  override def redoStep: Unit = controller.chessboard = controller.chessboard.setPiece(tile, piece)
