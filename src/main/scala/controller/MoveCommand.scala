package controller

import util.Command
import controller.Controller
import model.{Move, GameContext}
import scala.compiletime.uninitialized

final class MoveCommand(controller: Controller, ctx: GameContext) extends Command:
  private var before: GameContext = uninitialized
  private var after: GameContext = ctx

  override def doStep: Unit =
    before = controller.ctx
    controller.ctx = after

  override def undoStep: Unit =
    controller.ctx = before

  override def redoStep: Unit =
    controller.ctx = after
