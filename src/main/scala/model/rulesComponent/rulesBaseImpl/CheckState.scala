package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}
import model.rulesComponent.RulesInterface

final case class CheckState(playerInCheck: Color) extends GameState:
  override def name: String = s"$playerInCheck is in Check!"

  override def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidatorInterface): GameContext =
    if !v.validate(ctx, move) then
      println("Invalid Move! checkState")
      ctx
    else
      val newBoard = ctx.board.move(move)
      val nextPlayer = playerInCheck.opponent

      val nextState =
        if v.isCheck(newBoard, playerInCheck) then
          println("Invalid Move! You are in Check!")
          CheckState(playerInCheck)
        else if v.isDraw(newBoard, nextPlayer) then DrawState()
        else if v.isCheckmate(newBoard, nextPlayer) then CheckmateState(playerInCheck)
        else PlayingState

      ctx.copy(board = newBoard, currentPlayer = nextPlayer, state = nextState)
