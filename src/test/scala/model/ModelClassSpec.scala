package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import model.domain.*

class ModelClassSpec extends AnyWordSpec with Matchers:

  "Tile" should:

    "store file (x) and rank (y)" in:
      val t = Tile('e', 4)
      t.x shouldBe 'e'
      t.y shouldBe 4

    "support value equality" in:
      Tile('a', 1) shouldBe Tile('a', 1)
      Tile('a', 1) should not be Tile('a', 2)

  "Move" should:

    "store from and to tiles" in:
      val m = Move(Tile('e', 2), Tile('e', 4))
      m.from shouldBe Tile('e', 2)
      m.to shouldBe Tile('e', 4)

    "support value equality" in:
      Move(Tile('a', 2), Tile('a', 3)) shouldBe Move(Tile('a', 2), Tile('a', 3))
      Move(Tile('a', 2), Tile('a', 3)) should not be Move(Tile('a', 2), Tile('a', 4))

  "Color" should:

    "return the opposite color via opponent" in:
      White.opponent shouldBe Black
      Black.opponent shouldBe White

    "be stable (opponent of opponent returns the original color)" in:
      White.opponent.opponent shouldBe White
      Black.opponent.opponent shouldBe Black
