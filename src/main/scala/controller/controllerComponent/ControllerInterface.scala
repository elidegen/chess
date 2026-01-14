package controller.controllerComponent

import model.dataComponent.dataBaseImpl.{Chessboard, GameMode, Color}
import util.Observer

trait ControllerInterface {
  def chessboard: Chessboard

  def undo(): Unit
  def redo(): Unit

  def newGame(mode: GameMode): Unit
  def newGame(): Unit = newGame(GameMode.Classic) // damit controller.newGame() weiterhin geht

  def parseMove(input: String): Unit

  def add(observer: Observer): Unit
  def remove(observer: Observer): Unit

  def currentPlayer: Color
  def stateName: String
}
