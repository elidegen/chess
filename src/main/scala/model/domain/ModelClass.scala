package model.domain

case class Move(from: Tile, to: Tile)
case class Tile(x: Char, y: Int)

sealed trait Color {
  val opponent: Color
}

case object White extends Color {
  override val opponent: Color = Black
}

case object Black extends Color {
  override val opponent: Color = White
}
