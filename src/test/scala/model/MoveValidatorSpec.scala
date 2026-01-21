package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*
import model.rulesComponent.GameContext
import model.rulesComponent.rulesBaseImpl.PlayingState
import model.rulesComponent.MoveValidatorInterface
import model.rulesComponent.rulesBaseImpl.ClassicMoveValidator

class MoveValidatorSpec extends AnyWordSpec with Matchers:

  private def freshCtx(): GameContext =
    GameContext(board = Classic(), currentPlayer = White, state = PlayingState, gameMode = GameMode.Classic)
  given v: MoveValidatorInterface =
      ClassicMoveValidator()

  "MoveValidator.validate" should:

    "reject a move if the from-tile is empty" in:
      val ctx = freshCtx()

      val move = Move(Tile('a', 3), Tile('a', 4)) // a3 is empty in initial position
      v.validate(ctx, move) shouldBe false

    "reject a move if it is not the correct player's piece" in:
      val ctx = freshCtx()

      val move = Move(Tile('a', 7), Tile('a', 6))
      v.validate(ctx, move) shouldBe false

    "reject a move if the piece move strategy is illegal" in:
      val ctx = freshCtx()

      val move = Move(Tile('a', 2), Tile('a', 5))
      v.validate(ctx, move) shouldBe false

    "accept a legal basic move" in:
      val ctx = freshCtx()

      val move = Move(Tile('a', 2), Tile('a', 3))
      v.validate(ctx, move) shouldBe true
