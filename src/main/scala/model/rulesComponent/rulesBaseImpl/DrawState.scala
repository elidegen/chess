package model.rulesComponent.rulesBaseImpl

import model.domain.*

final case class DrawState() extends GameState:
  override def name: String = "Draw!"

  override def handleMove(ctx: GameContext, move: Move): GameContext = ctx
