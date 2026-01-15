import controller.controllerComponent.ControllerInterface
import model.rulesComponent.RulesInterface
import model.rulesComponent.{GameContext, GameState}
import controller.controllerComponent.controllerBaseImpl.Controller as ControllerBase
import model.rulesComponent.rulesBaseImpl.Rules as RulesBase
import model.domain.*

object ChessModule:
  given ControllerInterface = ControllerBase
  given RulesInterface = RulesBase
  given MoveValidatorj = ClassicMoveValidator

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
