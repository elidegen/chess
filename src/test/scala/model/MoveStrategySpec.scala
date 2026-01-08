package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MoveStrategySpec extends AnyWordSpec with Matchers:

  private def ctxWithBoard(board: Classic, current: Color = White): GameContext =
    GameContext(board = board, currentPlayer = current, state = PlayingState)

  "RookMoveStrategy" should:

    "allow straight moves on an empty board" in:
      val board = new Classic(Map(Tile('a', 1) -> Rook(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 1), Tile('a', 4))

      RookMoveStrategy.isLegal(ctx, move, Rook(White)) shouldBe true

    "reject diagonal moves" in:
      val board = new Classic(Map(Tile('a', 1) -> Rook(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 1), Tile('c', 3))

      RookMoveStrategy.isLegal(ctx, move, Rook(White)) shouldBe false

  "BishopMoveStrategy" should:

    "allow diagonal moves" in:
      val board = new Classic(Map(Tile('c', 1) -> Bishop(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('c', 1), Tile('f', 4))

      BishopMoveStrategy.isLegal(ctx, move, Bishop(White)) shouldBe true

    "reject straight moves" in:
      val board = new Classic(Map(Tile('c', 1) -> Bishop(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('c', 1), Tile('c', 3))

      BishopMoveStrategy.isLegal(ctx, move, Bishop(White)) shouldBe false

  "QueenMoveStrategy" should:

    "allow rook-like moves" in:
      val board = new Classic(Map(Tile('d', 1) -> Queen(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('d', 1), Tile('d', 6))

      QueenMoveStrategy.isLegal(ctx, move, Queen(White)) shouldBe true

    "allow bishop-like moves" in:
      val board = new Classic(Map(Tile('d', 1) -> Queen(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('d', 1), Tile('g', 4))

      QueenMoveStrategy.isLegal(ctx, move, Queen(White)) shouldBe true

    "reject illegal moves" in:
      val board = new Classic(Map(Tile('d', 1) -> Queen(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('d', 1), Tile('e', 3))

      QueenMoveStrategy.isLegal(ctx, move, Queen(White)) shouldBe false

  "KnightMoveStrategy" should:

    "allow L-shaped moves" in:
      val board = new Classic(Map(Tile('b', 1) -> Knight(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('b', 1), Tile('c', 3))

      KnightMoveStrategy.isLegal(ctx, move, Knight(White)) shouldBe true

    "reject non L-shaped moves" in:
      val board = new Classic(Map(Tile('b', 1) -> Knight(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('b', 1), Tile('b', 3))

      KnightMoveStrategy.isLegal(ctx, move, Knight(White)) shouldBe false

  "KingMoveStrategy" should:

    "allow one-step moves" in:
      val board = new Classic(Map(Tile('e', 1) -> King(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('e', 1), Tile('e', 2))

      KingMoveStrategy.isLegal(ctx, move, King(White)) shouldBe true

    "reject moves longer than one square" in:
      val board = new Classic(Map(Tile('e', 1) -> King(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('e', 1), Tile('e', 3))

      KingMoveStrategy.isLegal(ctx, move, King(White)) shouldBe false

  "PawnMoveStrategy" should:

    "allow a single forward move" in:
      val board = new Classic(Map(Tile('a', 2) -> Pawn(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 2), Tile('a', 3))

      PawnMoveStrategy.isLegal(ctx, move, Pawn(White)) shouldBe true

    "allow a double move from starting rank" in:
      val board = new Classic(Map(Tile('a', 2) -> Pawn(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 2), Tile('a', 4))

      PawnMoveStrategy.isLegal(ctx, move, Pawn(White)) shouldBe true

    "reject backward moves" in:
      val board = new Classic(Map(Tile('a', 2) -> Pawn(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 2), Tile('a', 1))

      PawnMoveStrategy.isLegal(ctx, move, Pawn(White)) shouldBe false

    "allow diagonal capture" in:
      val board = new Classic(Map(Tile('a', 2) -> Pawn(White), Tile('b', 3) -> Pawn(Black)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 2), Tile('b', 3))

      PawnMoveStrategy.isLegal(ctx, move, Pawn(White)) shouldBe true

    "reject diagonal move without capture" in:
      val board = new Classic(Map(Tile('a', 2) -> Pawn(White)))
      val ctx = ctxWithBoard(board)
      val move = Move(Tile('a', 2), Tile('b', 3))

      PawnMoveStrategy.isLegal(ctx, move, Pawn(White)) shouldBe false

  "EmptyMoveStrategy" should:

    "never allow a move" in:
      val ctx = ctxWithBoard(Classic())
      val move = Move(Tile('a', 1), Tile('a', 2))

      EmptyMoveStrategy.isLegal(ctx, move, Empty()) shouldBe false
