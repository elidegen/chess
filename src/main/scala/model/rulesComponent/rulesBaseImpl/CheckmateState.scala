package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}

final case class CheckmateState(playerInCheck: Color) extends GameState:
  override def name: String = s"Checkmate! ${playerInCheck.opponent} won!"

  override def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidatorInterface): GameContext = ctx
