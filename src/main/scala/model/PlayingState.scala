package model

object PlayingState extends GameState:
  override def name: String = "Playing"

  override def handleMove(ctx: GameContext, move: Any): GameContext =
    if !ctx.board.validateMove(move) then
      println("Invalid Move!")
      ctx
    else
      val newBoard = ctx.board.move(move)
      val nextPlayer = ctx.currentPlayer.opponent

      val nextState: GameState =
        if isCheckmate(newBoard, nextPlayer) then CheckmateState(nextPlayer)
        else if isCheck(newBoard, nextPlayer) then CheckState(nextPlayer)
        else if isDraw(newBoard, nextPlayer) then DrawState
        else PlayingState

      ctx.copy(board = newBoard, currentPlayer = nextPlayer, state = nextState)

private def isCheck(board: Chessboard, color: Color): Boolean = false
private def isCheckmate(board: Chessboard, color: Color): Boolean = false
private def isDraw(board: Chessboard, color: Color): Boolean = false
