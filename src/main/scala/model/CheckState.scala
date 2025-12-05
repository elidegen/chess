package model

final case class CheckState(playerInCheck: Color) extends GameState:
  override def name: String = s"Check($playerInCheck)"

  override def handleMove(ctx: GameContext, move: Move): GameContext =
    if !ctx.board.validateMove(move) then
      println("Invalid Move!")
      ctx
    else
      val newBoard = ctx.board.move(move)

      if isCheck(newBoard, playerInCheck) then
        println("Invalid Move! You are in Check!")
        ctx
      else if isDraw(newBoard, nextPlayer) then DrawState
      else PlayingState

      ctx.copy(board = newBoard, currentPlayer = nextPlayer, state = nextState)
