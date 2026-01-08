package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameStateSpec extends AnyWordSpec with Matchers:

  "GameContext.handleMove" should:

    "delegate the move handling to the current GameState" in:
      // Arrange: stub state that records invocation
      var called = false
      var receivedCtx: GameContext | Null = null
      var receivedMove: Move | Null = null

      object RecordingState extends GameState:
        override def name: String = "Recording"

        override def handleMove(ctx: GameContext, move: Move): GameContext =
          called = true
          receivedCtx = ctx
          receivedMove = move
          ctx // return unchanged context

      val board = Classic()
      val ctx = GameContext(board = board, currentPlayer = White, state = RecordingState)
      val move = Move(Tile('a', 2), Tile('a', 3))

      // Act
      val result = ctx.handleMove(move)

      // Assert
      called shouldBe true
      receivedCtx shouldBe ctx
      receivedMove shouldBe move
      result shouldBe ctx

  "GameContext.switchPlayer" should:

    "return a new GameContext with the opponent as currentPlayer" in:
      object DummyState extends GameState:
        override def name: String = "Dummy"
        override def handleMove(ctx: GameContext, move: Move): GameContext = ctx

      val board = Classic()
      val ctx = GameContext(board = board, currentPlayer = White, state = DummyState)

      val switched = ctx.switchPlayer

      switched.currentPlayer shouldBe Black
      switched.board shouldBe board
      switched.state shouldBe DummyState

    "not mutate the original GameContext" in:
      object DummyState extends GameState:
        override def name: String = "Dummy"
        override def handleMove(ctx: GameContext, move: Move): GameContext = ctx

      val ctx = GameContext(board = Classic(), currentPlayer = Black, state = DummyState)

      ctx.switchPlayer

      ctx.currentPlayer shouldBe Black
