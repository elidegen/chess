package model.fileIOCompononent

import model.rulesComponent.{GameContext}

trait FileIOInterface {
  def load: GameContext
  def save(ctx: GameContext): Unit
}
