   def createChessBoard(size:Int):String =
        ("+" + " - +" * size + "\n" + "|   "* size + "|\n")* (size) + "+" + " - +" * size

createChessBoard(4)
createChessBoard(1)