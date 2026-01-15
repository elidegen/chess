package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState}

final case class DrawState() extends GameState:
  override def name: String = "Draw!"

  override def handleMove(ctx: GameContext, move: Move)(using v: MoveValidator): GameContext = ctx
