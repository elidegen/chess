package model.rulesComponent

import model.domain.*

trait GameState:
  def name: String
  def handleMove(ctx: GameContext, move: Move)(using v: MoveValidator): GameContext

final case class GameContext(board: Chessboard, currentPlayer: Color, state: GameState):
  def handleMove(move: Move): GameContext =
    state.handleMove(this, move)

  def switchPlayer: GameContext =
    copy(currentPlayer = currentPlayer.opponent)
