import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TileSpec extends AnyWordSpec with Matchers:
  "a Tile" when:
    "return which piece is on it" should:
      val emptyTile = new Tile(x: Char, y: Int)
