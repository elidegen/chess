package model.rulesComponent

import model.dataComponent.dataBaseImpl.{Chessboard, Move, Color}
import model.rulesComponent.rulesBaseImpl.GameContext

trait RulesInterface {
  def validateMove(ctx: GameContext, move: Move): Boolean
  def applyMove(ctx: GameContext, move: Move): GameContext // ???

  def handleMove(ctx: GameContext, move: Move): GameContext

  def isCheck(board: Chessboard, color: Color): Boolean
  def isDraw(board: Chessboard, color: Color): Boolean
  def isCheckmate(board: Chessboard, color: Color): Boolean

  protected def createMoveValidator(): MoveValidator

  lazy val validator: MoveValidator = createMoveValidator()

  def validateMove(ctx: GameContext, move: Move): Boolean =
    validator.validate(ctx, move)
}
