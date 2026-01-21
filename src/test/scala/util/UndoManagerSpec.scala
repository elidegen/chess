package util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.ArrayBuffer

class UndoManagerSpec extends AnyWordSpec with Matchers:

  private final class AppendIntCommand(buf: ArrayBuffer[Int], value: Int) extends Command:
    override def doStep: Unit = buf += value
    override def undoStep: Unit = if buf.nonEmpty then buf.remove(buf.size - 1)
    override def redoStep: Unit = buf += value

  "UndoManager" should:

    "execute doStep, then undoStep, then redoStep in the expected order" in {
      val buf = ArrayBuffer.empty[Int]
      val um = new UndoManager

      um.doStep(AppendIntCommand(buf, 1))
      um.doStep(AppendIntCommand(buf, 2))
      buf.toList shouldBe List(1, 2)

      um.undoStep()
      buf.toList shouldBe List(1)

      um.redoStep()
      buf.toList shouldBe List(1, 2)
    }

    "clear the redo stack when a new command is executed after an undo" in {
      val buf = ArrayBuffer.empty[Int]
      val um = new UndoManager

      um.doStep(AppendIntCommand(buf, 1))
      um.doStep(AppendIntCommand(buf, 2))
      um.undoStep()
      buf.toList shouldBe List(1)

      // new command after undo => redo history must be discarded
      um.doStep(AppendIntCommand(buf, 3))
      buf.toList shouldBe List(1, 3)

      um.redoStep() // should be a no-op now
      buf.toList shouldBe List(1, 3)
    }

    "be a no-op when undo/redo is called with empty stacks" in {
      val buf = ArrayBuffer.empty[Int]
      val um = new UndoManager

      noException shouldBe thrownBy(um.undoStep())
      noException shouldBe thrownBy(um.redoStep())
      buf.toList shouldBe Nil
    }
