package controller.controllerComponent.controllerMockImpl

import controller.controllerComponent.ControllerInterface
import model.dataComponent.dataBaseImpl.{Chessboard, GameMode, GameFactory, StartPositions, Color, White}
import util.Observable

case class Controller() extends Observable with ControllerInterface {

  private var board: Chessboard =
    GameFactory.newGame(GameMode.Classic).board

  override def chessboard: Chessboard = board

  override def newGame(mode: GameMode): Unit =
    board = GameFactory.newGame(mode).board
    notifyObservers

  override def parseMove(input: String): Unit =
    println(s"[MOCK] parseMove called with: $input")
    notifyObservers

  override def undo(): Unit =
    println("[MOCK] undo")
    notifyObservers

  override def redo(): Unit =
    println("[MOCK] redo")
    notifyObservers

  override def currentPlayer: Color = White

  override def stateName: String = "Mock"
}
