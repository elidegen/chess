package model.rulesComponent.rulesBaseImpl

import model.domain.*

final case class CheckmateState(ctx: GameContext, playerInCheck: Color) extends GameState:
  override def name: String = s"Checkmate! ${playerInCheck.opponent} won!"

  override def handleMove(ctx: GameContext, move: Move)(using v: MoveValidator): GameContext = ctx
