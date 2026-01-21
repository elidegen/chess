package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}
import model.rulesComponent.RulesInterface
import model.fileIOCompononent.FileIOInterface

final class Rules(using v: MoveValidatorInterface, f: FileIOInterface) extends RulesInterface:

  override def handleMove(ctx: GameContext, move: Move): GameContext =
    ctx.state.handleMove(ctx, move)(using v, f)

  override def isCheck(board: Chessboard, color: Color): Boolean = v.isCheck(board, color)
  override def isDraw(board: Chessboard, color: Color): Boolean = v.isDraw(board, color)
  override def isCheckmate(board: Chessboard, color: Color): Boolean = v.isCheckmate(board, color)
