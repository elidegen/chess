package model.fileio

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.{Files, Paths}

import model.domain.*
import model.rulesComponent.*
import model.rulesComponent.rulesBaseImpl.*

class FileIOJsonSpec extends AnyWordSpec with Matchers:

  private val filePath = Paths.get("game.json")

  private def withCleanFile[A](body: => A): A =
    Files.deleteIfExists(filePath)
    try body
    finally Files.deleteIfExists(filePath)

  private def sampleCtx: GameContext =
    val board0: Chessboard = Classic()
    val board1 = board0.move(Move(Tile('a', 2), Tile('a', 3)))
    GameContext(
      board = board1,
      currentPlayer = Black,
      state = PlayingState,
      gameMode = GameMode.Classic)

  "JSON FileIO" should:

    "roundtrip a GameContext (save -> load)" in withCleanFile {
      val io = new model.fileIOCompononent.fileIOJSONImpl.FileIO
      val ctx = sampleCtx

      io.save(ctx)
      Files.exists(filePath) shouldBe true

      val loaded = io.load

      loaded.gameMode shouldBe ctx.gameMode
      loaded.currentPlayer shouldBe ctx.currentPlayer
      loaded.state.name shouldBe ctx.state.name

      loaded.board.getPiece(Tile('a', 2)) shouldBe Empty()
      loaded.board.getPiece(Tile('a', 3)) shouldBe Pawn(White)
      loaded.board.getPiece(Tile('e', 1)) shouldBe King(White)
      loaded.board.getPiece(Tile('e', 8)) shouldBe King(Black)
    }

    "fail with a clear exception on unknown game mode" in withCleanFile {
      val bad =
        """{
          |  "meta": { "mode": "Foo", "currentPlayer": "White", "state": { "name": "PlayingState" } },
          |  "board": []
          |}
          |""".stripMargin

      Files.writeString(filePath, bad)

      val io = new model.fileIOCompononent.fileIOJSONImpl.FileIO
      an[IllegalArgumentException] shouldBe thrownBy(io.load)
    }

    "fail with a clear exception on unknown piece type" in withCleanFile {
      val bad =
        """{
          |  "meta": { "mode": "Classic", "currentPlayer": "White", "state": { "name": "PlayingState" } },
          |  "board": [ { "type": "Dragon", "color": "White", "tile": "a1" } ]
          |}
          |""".stripMargin

      Files.writeString(filePath, bad)

      val io = new model.fileIOCompononent.fileIOJSONImpl.FileIO
      an[IllegalArgumentException] shouldBe thrownBy(io.load)
    }
