package model

case class Move(from: Tile, to: Tile)
case class Tile(x: Char, y: Int)

abstract class Color()
case class White() extends Color
case class Black() extends Color
