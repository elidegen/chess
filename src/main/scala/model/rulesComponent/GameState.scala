package model.rulesComponent

import model.domain.*
import model.fileIOCompononent.FileIOInterface

final case class GameContext(
    board: Chessboard,
    currentPlayer: Color,
    state: GameState,
    gameMode: GameMode):
  def handleMove(move: Move)(using v: MoveValidatorInterface, f: FileIOInterface): GameContext =
    state.handleMove(this, move)(using v, f)

  def switchPlayer: GameContext =
    copy(currentPlayer = currentPlayer.opponent)

trait GameState:
  def name: String
  def handleMove(ctx: GameContext, move: Move)(using
      v: MoveValidatorInterface,
      f: FileIOInterface): GameContext
