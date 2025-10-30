import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ChessSpec extends AnyWordSpec with Matchers{
    "createChessBoard" should {
        "create a 1*1 chessboard" in {
            Chess.createChessBoard(1) should be ("+ - +\n|   |\n+ - +")
        }
    }
}