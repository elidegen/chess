package controller.controllerComponent.controllerBaseImpl

import model.domain.*
import app.GameFactory
import util.{UndoManager, Observable}
import model.rulesComponent.RulesInterface
import controller.controllerComponent.ControllerInterface
import model.rulesComponent.{GameContext, GameState}

final class Controller(var ctx: GameContext)(using r: RulesInterface)
    extends Observable
    with ControllerInterface:
  def chessboard = ctx.board
  private val undoManager = new UndoManager()

  def newGame(mode: GameMode = GameMode.Classic): Unit =
    ctx = GameFactory.newGame(mode)
    notifyObservers

  def undo(): Unit =
    undoManager.undoStep()
    notifyObservers

  def redo(): Unit =
    undoManager.redoStep()
    notifyObservers

  def parseMove(input: String): Unit =
    val move = input.replace(" ", "")

    if (move.length != 4)
      println("invalid input! enter move like this: e2e4")
      return

    val fromX = move(0)
    val fromY = move(1).asDigit
    val toX = move(2)
    val toY = move(3).asDigit

    if (fromX < 'a' || fromX > 'h' || toX < 'a' || toX > 'h' || fromY < 1 || fromY > 8 || toY < 1 || toY > 8)
      println("invalid coordinates")
      return

    val ctxNew = r.handleMove(ctx, Move(Tile(fromX, fromY), Tile(toX, toY)))
    if (ctx == ctxNew) return;
    undoManager.doStep(new MoveCommand(this, ctxNew))

    notifyObservers

  def currentPlayer: Color = ctx.currentPlayer

  def stateName: String = ctx.state.name
