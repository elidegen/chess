

final class ModelWorksheet$_ {
def args = ModelWorksheet_sc.args$
def scriptPath = """ModelWorksheet.sc"""
/*<script>*/
// def createChessBoard(size: Int): String =
//   ("+" + " - +" * size + "\n" + "|   " * size + "|\n") * (size) + "+" + " - +" * size

// createChessBoard(4)
// createChessBoard(1)

def machSchachBrett(): String =
  val size = 8
  var board =
    ("+" + " - +" * size + "\n" + "| x " * size + "|\n") * (size) + "+" + " - +" * size
  for
    row <- 0 until size
    col <- 0 until size
  do board = board.replaceFirst("x", "p")
  board

machSchachBrett()

/*</script>*/ /*<generated>*//*</generated>*/
}

object ModelWorksheet_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new ModelWorksheet$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export ModelWorksheet_sc.script as `ModelWorksheet`

