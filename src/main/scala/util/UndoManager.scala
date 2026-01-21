package util

class UndoManager:
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(cmd: Command): Unit =
    cmd.doStep
    undoStack = cmd :: undoStack
    redoStack = Nil

  def undoStep(): Unit =
    undoStack match
      case cmd :: remain =>
        cmd.undoStep
        undoStack = remain
        redoStack = cmd :: redoStack
      case Nil =>

  def redoStep(): Unit =
    redoStack match
      case cmd :: remain =>
        cmd.redoStep
        redoStack = remain
        undoStack = cmd :: undoStack
      case Nil =>
