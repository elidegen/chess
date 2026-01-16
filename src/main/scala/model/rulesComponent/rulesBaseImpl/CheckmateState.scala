package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidator}

final case class CheckmateState(playerInCheck: Color) extends GameState:
  override def name: String = s"Checkmate! ${playerInCheck.opponent} won!"

  override def handleMove(ctx: GameContext, move: Move)(using v: MoveValidator): GameContext = ctx
