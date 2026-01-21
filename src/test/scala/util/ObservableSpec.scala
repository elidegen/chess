package util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyWordSpec with Matchers:

  private final class CountingObserver(ref: scala.collection.mutable.ArrayBuffer[Int]) extends Observer:
    override def update: Unit = ref += 1

  "Observable" should:

    "notify all subscribed observers" in {
      val hits = scala.collection.mutable.ArrayBuffer.empty[Int]
      val o1 = CountingObserver(hits)
      val o2 = CountingObserver(hits)
      val obs = new Observable

      obs.add(o1)
      obs.add(o2)

      obs.notifyObservers
      hits.size shouldBe 2
    }

    "not notify observers after they have been removed" in {
      val hits = scala.collection.mutable.ArrayBuffer.empty[Int]
      val o1 = CountingObserver(hits)
      val o2 = CountingObserver(hits)
      val obs = new Observable

      obs.add(o1)
      obs.add(o2)
      obs.remove(o1)

      obs.notifyObservers
      hits.size shouldBe 1
    }

    "handle removing an observer that is not currently subscribed" in {
      val hits = scala.collection.mutable.ArrayBuffer.empty[Int]
      val o1 = CountingObserver(hits)
      val obs = new Observable

      obs.remove(o1) // should not throw
      obs.notifyObservers
      hits.size shouldBe 0
    }
