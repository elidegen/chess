package model.rulesComponent.rulesBaseImpl

import model.domain.*

trait GameState:
  def name: String

final case class GameContext(board: Chessboard, currentPlayer: Color, state: GameState):
  def handleMove(move: Move): GameContext =
    state.handleMove(this, move)

  def switchPlayer: GameContext =
    copy(currentPlayer = currentPlayer.opponent)
