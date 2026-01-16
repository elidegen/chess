package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}

object PlayingState extends GameState:
  override def name: String = "Playing"

  override def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidatorInterface): GameContext =
    if !v.validate(ctx, move) then
      println("Invalid Move! playingState")
      ctx
    else
      val newBoard = ctx.board.move(move)
      val nextPlayer = ctx.currentPlayer.opponent

      val nextState: GameState =
        if v.isCheckmate(newBoard, nextPlayer) then CheckmateState(nextPlayer)
        else if v.isCheck(newBoard, nextPlayer) then CheckState(nextPlayer)
        else if v.isDraw(newBoard, nextPlayer) then DrawState()
        else PlayingState

      ctx.copy(board = newBoard, currentPlayer = nextPlayer, state = nextState)
