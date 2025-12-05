package model

trait GameState:
  def handleMove(ctx: GameContext, move: Move): GameContext
  def name: String

final case class GameContext(board: Chessboard, currentPlayer: Color, state: GameState):
  def handleMove(move: Move): GameContext =
    state.handleMove(this, move)

  def switchPlayer: GameContext =
    copy(currentPlayer = currentPlayer.opponent)

object NotSt
