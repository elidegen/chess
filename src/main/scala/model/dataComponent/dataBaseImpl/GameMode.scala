package model.dataComponent.dataBaseImpl

import model.rulesComponent.rulesBaseImpl.{PlayingState, GameContext}

enum GameMode:
  case Classic
  // case Chess960(seed: Long)
  // case Crazyhouse
  // case Antichess

object GameFactory:
  def newGame(mode: GameMode): GameContext =
    val board: Chessboard = mode match
      case GameMode.Classic =>
        Classic()
      // case GameMode.Crazyhouse =>
      //   Crazyhouse()
      // case GameMode.Antichess =>
      //   Antichess()
      case null =>
        Classic()
    GameContext(board = board, currentPlayer = White, state = PlayingState)
