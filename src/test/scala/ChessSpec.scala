import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ChessSpec extends AnyWordSpec with Matchers:
    "main" should:
        "print welcome to chess and render chessboard" in:
            val out = new java.io.ByteArrayOutputStream()
            Console.withOut(out):
                Chess.main(Array("2"))
            val output = out.toString()
            output should startWith("\nWelcome to Chess\n+ - +")
            output should endWith("+ - +\n")
        "print welcome and ask for size" in:
            val out = new java.io.ByteArrayOutputStream()
            val in = new java.io.ByteArrayInputStream("4\n".getBytes("UTF-8"))
            Console.withOut(out):
                Console.withIn(in):
                    Chess.main(Array())
            val output = out.toString()
            output should startWith("\nWelcome to Chess\nSpielbrettgröße in Int: ")
            output should endWith("+ - +\n")
    "createChessBoard" should :
        "create a 1*1 chessboard" in :
            Chess.createChessBoard(1) should be ("+ - +\n|   |\n+ - +")
    "getSize" should :
        "return the size of the chessboard" in :
            Chess.getSize(() => "5") should be (5)
        "retry until valid int is entered" in :
            var inputs = List("x", "3")
            val fake = () => 
                val h = inputs.head
                inputs = inputs.tail
                h
            Chess.getSize(fake) should be (3)
