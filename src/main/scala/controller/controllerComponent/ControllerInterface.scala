package controller.controllerComponent

import model.domain.*
import util.Observer

trait ControllerInterface {
  def chessboard: Chessboard

  def undo(): Unit
  def redo(): Unit

  def newGame(gameMode: GameMode): Unit
  def newGame(): Unit = newGame(GameMode.Classic)

  def parseMove(input: String): Unit

  def add(observer: Observer): Unit
  def remove(observer: Observer): Unit

  def currentPlayer: Color
  def stateName: String
  def gameMode: GameMode

  def loadFromFile(path: String = "game.xml"): Boolean
}
