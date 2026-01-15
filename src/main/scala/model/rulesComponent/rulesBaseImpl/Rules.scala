package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState}
import model.rulesComponent.RulesInterface

final class Rules(using r: RulesInterface, v: MoveValidator)
    extends model.rulesComponent.RulesInterface:
  override def validateMove(ctx: GameContext, move: Move): Boolean =
    r.validateMove(ctx, move)

  override def handleMove(ctx: GameContext, move: Move): GameContext =
    ctx.state.handleMove(ctx, move)

  override def isCheck(board: Chessboard, color: Color): Boolean = v.isCheck(board, color)
  override def isDraw(board: Chessboard, color: Color): Boolean = v.isDraw(board, color)
  override def isCheckmate(board: Chessboard, color: Color): Boolean = v.isCheckmate(board, color)
