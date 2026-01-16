package model.rulesComponent.rulesBaseImpl

import model.domain.*
import model.rulesComponent.{GameContext, GameState, MoveValidatorInterface}
import model.fileIOCompononent.FileIOInterface

final case class CheckmateState(playerInCheck: Color) extends GameState:
  override def name: String = s"Checkmate! ${playerInCheck.opponent} won!"

  override def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidatorInterface,
      f: FileIOInterface): GameContext = ctx
