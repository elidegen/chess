package app

import controller.controllerComponent.ControllerInterface
import controller.controllerComponent.controllerBaseImpl.Controller as ControllerBase
import model.domain.*
import model.rulesComponent.{GameContext, RulesInterface, MoveValidatorInterface}
import model.rulesComponent.rulesBaseImpl.{Rules, ClassicMoveValidator, PlayingState}
// import model.fileIOCompononent.fileIOXMLImpl.{FileIO}
import model.fileIOCompononent.fileIOJSONImpl.{FileIO}
import model.fileIOCompononent.FileIOInterface

object ChessModule:

  // Rules dependencies
  given moveValidator: MoveValidatorInterface = ClassicMoveValidator()
  given fileIO: FileIOInterface = FileIO()
  given rules: RulesInterface = Rules()

  // Initial game context
  private val initialCtx: GameContext = GameFactory.newGame(GameMode.Classic)

  // Controller (depends on RulesInterface)
  given controller: ControllerInterface =
    new ControllerBase(initialCtx)(using rules)

object GameFactory:
  def newGame(gameMode: GameMode): GameContext =
    val board: Chessboard = gameMode match
      case GameMode.Classic =>
        Classic()

    GameContext(board = board, currentPlayer = White, state = PlayingState, gameMode = gameMode)
