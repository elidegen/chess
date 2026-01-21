package model.fileio

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.{Files, Paths}

import model.domain.*
import model.rulesComponent.*
import model.rulesComponent.rulesBaseImpl.*

class FileIOXmlSpec extends AnyWordSpec with Matchers:

  private val filePath = Paths.get("game.xml")

  private def withCleanFile[A](body: => A): A =
    Files.deleteIfExists(filePath)
    try body
    finally Files.deleteIfExists(filePath)

  private def sampleCtx: GameContext =
    val board0: Chessboard = Classic()
    val board1 = board0.move(Move(Tile('b', 2), Tile('b', 4)))
    GameContext(
      board = board1,
      currentPlayer = Black,
      state = PlayingState,
      gameMode = GameMode.Classic)

  "XML FileIO" should:

    "roundtrip a GameContext (save -> load)" in withCleanFile {
      val io = new model.fileIOCompononent.fileIOXMLImpl.FileIO
      val ctx = sampleCtx

      io.save(ctx)
      Files.exists(filePath) shouldBe true

      val loaded = io.load

      loaded.gameMode shouldBe ctx.gameMode
      loaded.currentPlayer shouldBe ctx.currentPlayer
      loaded.state.name shouldBe ctx.state.name

      loaded.board.getPiece(Tile('b', 2)) shouldBe Empty()
      loaded.board.getPiece(Tile('b', 4)) shouldBe Pawn(White)
      loaded.board.getPiece(Tile('e', 1)) shouldBe King(White)
      loaded.board.getPiece(Tile('e', 8)) shouldBe King(Black)
    }

    "fail with a clear exception on unknown game mode" in withCleanFile {
      val bad =
        """<game>
          |  <meta>
          |    <mode>Foo</mode>
          |    <currentPlayer>White</currentPlayer>
          |    <state name="PlayingState"/>
          |  </meta>
          |  <board/>
          |</game>""".stripMargin

      Files.writeString(filePath, bad)

      val io = new model.fileIOCompononent.fileIOXMLImpl.FileIO
      an[IllegalArgumentException] shouldBe thrownBy(io.load)
    }

    "fail with a clear exception on unknown piece type" in withCleanFile {
      val bad =
        """<game>
          |  <meta>
          |    <mode>Classic</mode>
          |    <currentPlayer>White</currentPlayer>
          |    <state name="PlayingState"/>
          |  </meta>
          |  <board>
          |    <piece type="Dragon" color="White" tile="a1"/>
          |  </board>
          |</game>""".stripMargin

      Files.writeString(filePath, bad)

      val io = new model.fileIOCompononent.fileIOXMLImpl.FileIO
      an[IllegalArgumentException] shouldBe thrownBy(io.load)
    }
