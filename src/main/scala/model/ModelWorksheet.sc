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
