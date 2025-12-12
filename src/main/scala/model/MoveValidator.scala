package model

// color is the player that made the last move
abstract class MoveValidator():
  def validate(ctx: GameContext, move: Move): Boolean
  def isCheck(board: Chessboard, color: Color): Boolean
  def isDraw(board: Chessboard, color: Color): Boolean
  def isCheckmate(board: Chessboard, color: Color): Boolean
