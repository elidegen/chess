package app

import controller.controllerComponent.ControllerInterface
import controller.controllerComponent.controllerBaseImpl.Controller as ControllerBase
import model.domain.*
import model.rulesComponent.{GameContext, RulesInterface, MoveValidatorInterface}
import model.rulesComponent.rulesBaseImpl.{Rules as RulesBase, ClassicMoveValidator, PlayingState}

object ChessModule:

  // Rules dependencies
  given moveValidator: MoveValidatorInterface = ClassicMoveValidator()
  given rules: RulesInterface = RulesBase()

  // Initial game context
  private val initialCtx: GameContext = GameFactory.newGame(GameMode.Classic)

  // Controller (depends on RulesInterface)
  given controller: ControllerInterface =
    new ControllerBase(initialCtx)(using rules)

object GameFactory:
  def newGame(mode: GameMode): GameContext =
    val board: Chessboard = mode match
      case GameMode.Classic =>
        Classic()

    GameContext(board = board, currentPlayer = White, state = PlayingState)
