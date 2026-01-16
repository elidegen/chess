package model.fileIOCompononent.fileIOJSONImpl

import model.fileIOCompononent.FileIOInterface
import model.rulesComponent.GameContext
import model.domain.*
import model.rulesComponent.rulesBaseImpl.*

import ujson.*
import java.nio.file.{Files, Paths}

final class FileIO extends FileIOInterface {

  private val filePath = "game.json"

  override def save(ctx: GameContext): Unit =
    val json = gameToJson(ctx)
    Files.writeString(Paths.get(filePath), json.render(2))

  override def load: GameContext =
    val jsonStr = Files.readString(Paths.get(filePath))
    val json = ujson.read(jsonStr)
    jsonToGame(json)

  private def gameToJson(ctx: GameContext): Value =
    Obj(
      "meta" -> Obj(
        "mode" -> ctx.gameMode.toString,
        "currentPlayer" -> ctx.currentPlayer.toString,
        "state" -> Obj("name" -> ctx.state.name)),
      "board" -> Arr.from(boardToJson(ctx.board)))

  private def boardToJson(board: Chessboard): Seq[Value] =
    val tiles =
      for
        y <- 1 to 8
        x <- 'a' to 'h'
      yield Tile(x, y)

    tiles.toSeq.flatMap { t =>
      board.getPiece(t) match
        case _: Empty => None
        case p =>
          val (tpe, col) = pieceToStrings(p)
          Some(Obj("type" -> tpe, "color" -> col, "tile" -> tileToString(t)))
    }

  private def jsonToGame(json: Value): GameContext =
    val meta = json("meta")

    val modeStr = meta("mode").str
    val currentPlayerStr = meta("currentPlayer").str
    val stateName = meta("state")("name").str

    val pieces = json("board").arr.map { p =>
      val tpe = p("type").str
      val col = p("color").str
      val tile = stringToTile(p("tile").str)
      tile -> stringsToPiece(tpe, col)
    }.toMap

    val mode = stringToGameMode(modeStr)
    val board: Chessboard = mode match
      case GameMode.Classic => new Classic(pieces)

    val currentPlayer = stringToColor(currentPlayerStr)
    val state = stateFromStrings(stateName)

    GameContext(board = board, currentPlayer = currentPlayer, state = state, gameMode = mode)

  private def tileToString(t: Tile): String =
    s"${t.x}${t.y}"

  private def stringToTile(s: String): Tile =
    val x = s.charAt(0)
    val y = s.substring(1).toInt
    Tile(x, y)

  private def stringToGameMode(s: String): GameMode = s match
    case "Classic" => GameMode.Classic
    case other => throw new IllegalArgumentException(s"Unknown GameMode: $other")

  private def stringToColor(s: String): Color = s match
    case "White" => White
    case "Black" => Black
    case other => throw new IllegalArgumentException(s"Unknown Color: $other")

  private def pieceToStrings(p: Piece): (String, String) = p match
    case Pawn(c) => ("Pawn", c.toString)
    case Rook(c) => ("Rook", c.toString)
    case Knight(c) => ("Knight", c.toString)
    case Bishop(c) => ("Bishop", c.toString)
    case Queen(c) => ("Queen", c.toString)
    case King(c) => ("King", c.toString)
    case _: Empty => ("Empty", "None")

  private def stringsToPiece(tpe: String, col: String): Piece =
    val c = stringToColor(col)
    tpe match
      case "Pawn" => Pawn(c)
      case "Rook" => Rook(c)
      case "Knight" => Knight(c)
      case "Bishop" => Bishop(c)
      case "Queen" => Queen(c)
      case "King" => King(c)
      case other => throw new IllegalArgumentException(s"Unknown piece type: $other")

  private def stateFromStrings(name: String): model.rulesComponent.GameState =
    name match
      case "PlayingState" | "Playing" => PlayingState
      case "DrawState" | "Draw" => DrawState()
      case other => throw new IllegalArgumentException(s"Unknown state: $other")
}
