package model.fileIOCompononent.fileIOXMLImpl

import model.fileIOCompononent.FileIOInterface
import model.rulesComponent.GameContext
import model.domain.*
import model.rulesComponent.rulesBaseImpl.*
import scala.xml.*

final class FileIO extends FileIOInterface {

  private val filePath = "game.xml"

  override def save(ctx: GameContext): Unit =
    val xml = gameToXml(ctx)
    XML.save(filePath, xml, "UTF-8", xmlDecl = true, doctype = null)

  override def load: GameContext =
    val xml = XML.loadFile(filePath)
    xmlToGame(xml)

  private def gameToXml(ctx: GameContext): Elem =
    <game>
      <meta>
        <mode>{ctx.gameMode.toString}</mode>
        <currentPlayer>{ctx.currentPlayer.toString}</currentPlayer>
        {stateToXml(ctx.state)}
      </meta>
      <board>
        {boardToXml(ctx.board)}
      </board>
    </game>

  private def stateToXml(state: model.rulesComponent.GameState): Elem =
    <state name={state.name}/>

  private def boardToXml(board: Chessboard): Seq[Elem] =
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
          Some(<piece type={tpe} color={col} tile={tileToString(t)}/>)
    }

  private def xmlToGame(xml: Elem): GameContext =
    val modeStr = (xml \\ "mode").text.trim
    val currentPlayerStr = (xml \\ "currentPlayer").text.trim

    val stateNode = (xml \\ "state").head
    val stateName = (stateNode \@ "name").trim
    val params = (stateNode \\ "param").map { n =>
      (n \@ "key") -> (n \@ "value")
    }.toMap

    val pieces = (xml \\ "board" \\ "piece").map { n =>
      val tpe = n \@ "type"
      val col = n \@ "color"
      val tile = stringToTile(n \@ "tile")
      tile -> stringsToPiece(tpe, col)
    }.toMap

    val mode = stringToGameMode(modeStr)
    val board: Chessboard = mode match
      case GameMode.Classic => new Classic(pieces)

    val currentPlayer = stringToColor(currentPlayerStr)
    val state = stateFromStrings(stateName, params)

    GameContext(board = board, currentPlayer = currentPlayer, state = state, gameMode = mode)

  private def tileToString(t: Tile): String =
    s"${t.x}${t.y}"

  private def stringToTile(s: String): Tile =
    val x = s.charAt(0)
    val y = s.substring(1).toInt
    Tile(x, y)

  private def stringToGameMode(s: String): GameMode =
    s match
      case "Classic" => GameMode.Classic
      case other => throw new IllegalArgumentException(s"Unknown GameMode: $other")

  private def stringToColor(s: String): Color =
    s match
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

  private def stateFromStrings(
      name: String,
      params: Map[String, String]): model.rulesComponent.GameState =
    name match
      case "PlayingState" | "Playing" => PlayingState
      case "DrawState" | "Draw" => DrawState()
      case other => throw new IllegalArgumentException(s"Unknown state: $other")
}
