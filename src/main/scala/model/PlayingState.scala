package model

object PlayingState extends GameState:
  override def name: String = "Playing"

  override def handleMove(ctx: GameContext, move: Move): GameContext =
    if !ctx.board.validateMove(ctx, move) then
      println("Invalid Move! playingState")
      ctx
    else
      val newBoard = ctx.board.move(move)
      val nextPlayer = ctx.currentPlayer.opponent

      val nextState: GameState =
        if ctx.board.validator.isCheckmate(newBoard, nextPlayer) then
          CheckmateState(ctx, nextPlayer)
        else if ctx.board.validator.isCheck(newBoard, nextPlayer) then CheckState(ctx, nextPlayer)
        else if ctx.board.validator.isDraw(newBoard, nextPlayer) then DrawState()
        else PlayingState

      ctx.copy(board = newBoard, currentPlayer = nextPlayer, state = nextState)
