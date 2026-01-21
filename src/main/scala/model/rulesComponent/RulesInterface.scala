package model.rulesComponent

import model.domain.*

trait RulesInterface {
  def handleMove(ctx: GameContext, move: Move): GameContext

  def isCheck(board: Chessboard, color: Color): Boolean
  def isDraw(board: Chessboard, color: Color): Boolean
  def isCheckmate(board: Chessboard, color: Color): Boolean
}
