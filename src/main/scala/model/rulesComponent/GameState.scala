package model.rulesComponent

import model.domain.*

final case class GameContext(board: Chessboard, currentPlayer: Color, state: GameState):
  def handleMove(move: Move)(using v: MoveValidatorInterface): GameContext =
    state.handleMove(this, move)

  def switchPlayer: GameContext =
    copy(currentPlayer = currentPlayer.opponent)

trait GameState:
  def name: String
  def handleMove(ctx: GameContext, move: Move)(using v: MoveValidatorInterface): GameContext
