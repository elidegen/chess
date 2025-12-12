package model
final case class DrawState() extends GameState:
  override def name: String = s"Draw!"

  override def handleMove(ctx: GameContext, move: Move): GameContext = ctx
