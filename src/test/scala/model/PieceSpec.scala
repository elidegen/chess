package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PieceSpec extends AnyWordSpec with Matchers:
  "toString()" should:
    "print the correct emoji" in:
      val whitePawn = Pawn(White)
      whitePawn.toString shouldBe "♙"
