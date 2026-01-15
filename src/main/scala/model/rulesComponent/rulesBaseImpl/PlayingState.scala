package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.RulesInterface

object PlayingState extends GameState:
  override def name: String = "Playing"

  override def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidator,
      r: RulesInterface): GameContext =
    if !r.validateMove(ctx, move) then
      println("Invalid Move! playingState")
      ctx
    else
      val newBoard = ctx.board.move(move)
      val nextPlayer = ctx.currentPlayer.opponent

      val nextState: GameState =
        if v.isCheckmate(newBoard, nextPlayer) then CheckmateState(ctx, nextPlayer)
        else if v.isCheck(newBoard, nextPlayer) then CheckState(ctx, nextPlayer)
        else if v.isDraw(newBoard, nextPlayer) then DrawState()
        else PlayingState

      ctx.copy(board = newBoard, currentPlayer = nextPlayer, state = nextState)
