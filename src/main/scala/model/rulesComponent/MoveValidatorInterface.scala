package model.rulesComponent

import model.domain.*

trait MoveValidatorInterface:
  def validate(ctx: GameContext, move: Move): Boolean
  def isCheck(board: Chessboard, color: Color): Boolean
  def isDraw(board: Chessboard, color: Color): Boolean
  def isCheckmate(board: Chessboard, color: Color): Boolean
