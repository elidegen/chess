package model

abstract class MoveValidator():
  def validate(move: Move, cb: Chessboard): Boolean
