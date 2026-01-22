package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.{GameState, GameContext, MoveValidatorInterface}
import model.rulesComponent.rulesBaseImpl.{CheckmateState, PlayingState, ClassicMoveValidator}
import model.fileIOCompononent.FileIOInterface

final class CheckMateStateSpec extends AnyWordSpec with Matchers:

  private def ctxWith(state: GameState, current: Color = White): GameContext =
    GameContext(board = Classic(), currentPlayer = current, state = state, GameMode.Classic)

  "CheckmateState" should:

    "indicate winner" in:
      val stateWhiteInCheck = CheckmateState(playerInCheck = White)
      stateWhiteInCheck.name shouldBe "Checkmate! Black won!"

      val stateBlackInCheck = CheckmateState(playerInCheck = Black)
      stateBlackInCheck.name shouldBe "Checkmate! White won!"

    "reject further moves by returning context unchanged" in:
      given v: MoveValidatorInterface =
        ClassicMoveValidator()
      given f: FileIOInterface with
        override def load: GameContext =
          throw new UnsupportedOperationException("not used in this test")

        override def save(ctx: GameContext): Unit = ()
        
      val state = CheckmateState(playerInCheck = White)
      val ctx = ctxWith(state)

      val beforeBoard = ctx.board
      val beforePlayer = ctx.currentPlayer
      val beforeState = ctx.state

      val result = state.handleMove(ctx, Move(Tile('a', 2), Tile('a', 3)))

      result shouldBe ctx
      result.board shouldBe beforeBoard
      result.currentPlayer shouldBe beforePlayer
      result.state shouldBe beforeState
