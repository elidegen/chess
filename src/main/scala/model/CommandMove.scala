package Model

case class MoveCommand(from: Tile, to: Tile, piece: Piece, capturedPiece: Option[Piece], board: Chessboard) extends Command
  override def doStep(): Chessboard =
    val newBoard = chessboard.move(Move(from, to))
    capturedPiece.foreach(captured => newBoard.removePiece(captured))
    newBoard

  override def undoStep(): Chessboard =
    val newBoard = chessboard.move(Move(to, from)) // Bewege die Figur zurück
    capturedPiece.foreach(captured => newBoard.addPiece(captured, to)) // Setze die geschlagene Figur zurück
    newBoard


  override def redoStep(): Chessboard = doStep() // Wiederhole einfach den Zug

