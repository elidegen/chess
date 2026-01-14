package aview

import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{GridPane, HBox, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.Stage
import controller.controllerComponent.ControllerInterface
import model.dataComponent.dataBaseImpl.{Bishop, Black, Chessboard, Empty, GameMode, King, Knight, Pawn, Queen, Rook, Tile, White, Piece}
import util.Observer
import scalafx.scene.control.Hyperlink
import scalafx.scene.text.{Font, Text, TextFlow}
import java.awt.Desktop
import java.net.URI
import scalafx.scene.layout.Region
import scalafx.scene.layout.Priority

final class Gui(controller: ControllerInterface) extends Observer:
  controller.add(this)

  private val statusLabel = new Label("")

  private val quit = new Button("Quit")
  private val newGame = new Button("New")
  private val mode = new Button("Mode")
  private val undo = new Button("Undo")
  private val redo = new Button("Redo")

  private val squares: Array[Array[ImageView]] =
    Array.fill(8, 8)(new ImageView:
      fitWidth = 56
      fitHeight = 56
      preserveRatio = true
      smooth = true)

  private val boardGrid: GridPane = new GridPane:
    hgap = 0
    vgap = 0

  private val backgrounds: Array[Array[Rectangle]] =
    Array.fill(8, 8)(new Rectangle {
      width = 64
      height = 64
    })

  private val overlays: Array[Array[Rectangle]] =
    Array.fill(8, 8)(new Rectangle {
      width = 64
      height = 64
      fill = Color.rgb(220, 20, 60, 0.4)
      visible = false
    })

  private var selected: Option[(Int, Int)] = None

  for (row <- 0 until 8; col <- 0 until 8) do
    val bg = backgrounds(col)(row)
    bg.fill = if (lightSquare(col, row)) Color.rgb(240, 217, 181) else Color.rgb(181, 136, 99)

    val isLight = lightSquare(col, row)

    val rankLabel: Option[Text] =
      if col == 0 then
        Some(new Text((row + 1).toString) {
          fill = coordTextColor(isLight)
          font = Font(12)
        })
      else None

    val fileLabel: Option[Text] =
      if row == 0 then
        Some(new Text((('a'.toInt + col).toChar).toString) {
          fill = coordTextColor(isLight)
          font = Font(12)
        })
      else None

    val cell = new StackPane:
      alignment = Pos.Center
      children =
        Seq(bg, overlays(col)(row), squares(col)(row)) ++ rankLabel.toSeq ++ fileLabel.toSeq

    // Position coordinate labels inside the tile
    rankLabel.foreach { t =>
      StackPane.setAlignment(t, Pos.TopLeft)
      StackPane.setMargin(t, Insets(3, 0, 0, 3))
    }

    fileLabel.foreach { t =>
      StackPane.setAlignment(t, Pos.BottomRight)
      // top, right, bottom, left
      StackPane.setMargin(t, Insets(0, 4, 2, 0))
    }

    cell.onMouseClicked = _ =>
      selected match
        case None =>
          selected = Some(col -> row)
          clearHighlights
          highlight(col, row)
        case Some((selectedCol, selectedRow)) if selectedCol == col && selectedRow == row => // deselect if already selected
          selected = None
          clearHighlights
        case Some((selectedCol, selectedRow)) =>
          val from = tileAt(selectedCol, selectedRow)
          val to = tileAt(col, row)
          val moveString = s"${from.x}${from.y}${to.x}${to.y}"
          selected = None
          clearHighlights
          controller.parseMove(moveString)

    boardGrid.add(cell, col, 7 - row) // y=0 (rank 1) at bottom

  private def lightSquare(x: Int, y: Int): Boolean = (x + y) % 2 == 0

  private def coordTextColor(isLight: Boolean): Color =
    // Use the opposite tile color for readability
    if isLight then Color.rgb(181, 136, 99) else Color.rgb(240, 217, 181)

  private def tileAt(col: Int, row: Int): Tile =
    Tile(('a'.toInt + col).toChar, row + 1)

  private def highlight(col: Int, row: Int) =
    overlays(col)(row).visible = true

  private def clearHighlights =
    for (row <- 0 until 8; col <- 0 until 8) do overlays(col)(row).visible = false

  private def pieceImagePath(boardPiece: Piece): Option[String] =
    boardPiece match
      case Pawn(White) => Some("/images/pw.png")
      case Rook(White) => Some("/images/rw.png")
      case Knight(White) => Some("/images/nw.png")
      case Bishop(White) => Some("/images/bw.png")
      case Queen(White) => Some("/images/qw.png")
      case King(White) => Some("/images/kw.png")
      case Pawn(Black) => Some("/images/pb.png")
      case Rook(Black) => Some("/images/rb.png")
      case Knight(Black) => Some("/images/nb.png")
      case Bishop(Black) => Some("/images/bb.png")
      case Queen(Black) => Some("/images/qb.png")
      case King(Black) => Some("/images/kb.png")
      case _: Empty => None
      case _ => None

  private lazy val imageCache: Map[String, Image] =
    Seq(
      "/images/pw.png",
      "/images/rw.png",
      "/images/nw.png",
      "/images/bw.png",
      "/images/qw.png",
      "/images/kw.png",
      "/images/pb.png",
      "/images/rb.png",
      "/images/nb.png",
      "/images/bb.png",
      "/images/qb.png",
      "/images/kb.png").flatMap { path =>
      Option(getClass.getResourceAsStream(path)).map { s =>
        try path -> Image(s)
        finally s.close()
      }
    }.toMap

  private def cachedImage(path: String): Option[Image] =
    imageCache.get(path)

  private def renderBoard(board: Chessboard): Unit =
    for (y <- 0 until 8; x <- 0 until 8) do
      val tile = Tile((('a'.toInt + x).toChar), y + 1)
      val piece = board.getPiece(tile)
      val imgOption = pieceImagePath(piece).flatMap(cachedImage)
      squares(x)(y).image = imgOption.orNull

  private def renderStatus(): Unit =
    statusLabel.text =
      s"State: ${controller.stateName}  |  Current Player: ${controller.currentPlayer}"

  val infoText = new Text("Icons provided by")

  val helpLink = new Hyperlink("Sharechess") {
    textFill = Color.Blue
    underline = true
    onAction = _ => {
      Desktop.getDesktop.browse(new URI("https://sharechess.github.io/"))
    }
  }

  val statusFlow = new TextFlow {

    children = Seq(infoText, helpLink)
  }

  val spacer = new Region
  HBox.setHgrow(spacer, Priority.Always)
  HBox.setMargin(statusFlow, Insets(6, 0, 0, 0)) // top, right, bottom, left

  undo.onAction = _ => controller.undo()
  redo.onAction = _ => controller.redo()
  newGame.onAction = _ => controller.newGame(GameMode.Classic)
  quit.onAction = _ => Platform.runLater(() => Platform.exit())

  private val rootNode: VBox = new VBox {
    spacing = 10
    padding = Insets(12)
    children = Seq(
      statusLabel,
      boardGrid,
      new HBox {
        spacing = 8
        children = Seq(undo, redo, newGame, quit, spacer, statusFlow)
      })
  }

  private def render(): Unit =
    renderStatus()
    renderBoard(controller.chessboard)

  render()

  override def update: Unit =
    Platform.runLater(() => render())

  def createScene(): Scene =
    new Scene {
      root = rootNode
    }

object Gui:
  @volatile private var started = false

  def start(controller: ControllerInterface): Unit =
    if started then return
    started = true

    javafx.application.Platform.setImplicitExit(false)

    Platform.startup(() => {
      val gui = new Gui(controller)
      val stage = new Stage {
        title = "Chess"
        scene = gui.createScene()
      }
      stage.show()
    })
