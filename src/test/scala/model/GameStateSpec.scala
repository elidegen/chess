package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.GameContext
import model.rulesComponent.GameState
import model.rulesComponent.MoveValidatorInterface
import model.rulesComponent.rulesBaseImpl.ClassicMoveValidator
import model.fileIOCompononent.FileIOInterface

class GameStateSpec extends AnyWordSpec with Matchers:

  given v: MoveValidatorInterface =
    ClassicMoveValidator()
  given f: FileIOInterface with
    override def load: GameContext =
      throw new UnsupportedOperationException("not used in this test")
    override def save(ctx: GameContext): Unit = ()

  "GameContext.handleMove" should:

    "delegate the move handling to the current GameState" in:
      // Arrange: stub state that records invocation
      var called = false
      var receivedCtx: Option[GameContext] = None
      var receivedMove: Option[Move] = None

      object RecordingState extends GameState:
        override def name: String = "Recording"

        override def handleMove(
          ctx: GameContext,
          move: Move
        )(using
          v: MoveValidatorInterface,
          f: FileIOInterface
        ): GameContext =
          called = true
          receivedCtx = Some(ctx)
          receivedMove = Some(move)
          ctx

      val board = Classic()
      val ctx = GameContext(board = board, currentPlayer = White, state = RecordingState, gameMode = GameMode.Classic)
      val move = Move(Tile('a', 2), Tile('a', 3))

      // Act
      val result = ctx.handleMove(move)

      // Assert
      called shouldBe true
      receivedCtx shouldBe Some(ctx)
      receivedMove shouldBe Some(move)
      result shouldBe ctx

  "GameContext.switchPlayer" should:

    "return a new GameContext with the opponent as currentPlayer" in:
      object DummyState extends GameState:
        override def name: String = "Dummy"
        override def handleMove(
          ctx: GameContext,
          move: Move
        )(using
          v: MoveValidatorInterface,
          f: FileIOInterface
        ): GameContext = ctx

      val board = Classic()
      val ctx = GameContext(board = board, currentPlayer = White, state = DummyState, gameMode = GameMode.Classic)

      val switched = ctx.switchPlayer

      switched.currentPlayer shouldBe Black
      switched.board shouldBe board
      switched.state shouldBe DummyState

    "not mutate the original GameContext" in:

      object DummyState extends GameState:
        override def name: String = "Dummy"
        override def handleMove(
          ctx: GameContext,
          move: Move
        )(using
          v: MoveValidatorInterface,
          f: FileIOInterface
        ): GameContext = ctx

      val ctx = GameContext(board = Classic(), currentPlayer = Black, state = DummyState, gameMode = GameMode.Classic)

      ctx.switchPlayer

      ctx.currentPlayer shouldBe Black
