package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}
import model.fileIOCompononent.FileIOInterface

final case class DrawState() extends GameState:
  override def name: String = "Draw!"

  override def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidatorInterface,
      f: FileIOInterface): GameContext = ctx
