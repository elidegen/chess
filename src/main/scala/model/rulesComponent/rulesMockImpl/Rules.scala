package model.rulesComponent.rulesMockImpl

import model.rulesComponent.RulesInterface
import model.rulesComponent.rulesBaseImpl.{GameContext, PlayingState}
import model.dataComponent.dataBaseImpl.{Chessboard, Move, Color}

final case class Rules() extends RulesInterface {

  override def validateMove(ctx: GameContext, move: Move): Boolean =
    move.from != move.to

  override def handleMove(ctx: GameContext, move: Move): GameContext = {
    if (!validateMove(ctx, move)) ctx
    else {
      val newBoard: Chessboard = ctx.board.move(move)
      ctx.copy(board = newBoard, state = PlayingState).switchPlayer
    }
  }

  override def isCheck(board: Chessboard, color: Color): Boolean = false
  override def isDraw(board: Chessboard, color: Color): Boolean = false
  override def isCheckmate(board: Chessboard, color: Color): Boolean = false
}
