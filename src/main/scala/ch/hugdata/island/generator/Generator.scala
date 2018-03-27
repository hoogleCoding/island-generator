package ch.hugdata.island.generator

import ch.hugdata.island.graph.Point2D
import ch.hugdata.island.svgwriter.Dimensions

import scala.annotation.tailrec
import scala.util.Random

/**
  * Generator of random points
  */

class Generator(private val seed: Int) {

  private val random = new Random(seed)

  final def generatePoint(limits: Dimensions): Point2D = {
    val xLocation = limits.minX + (random.nextDouble() * (limits.maxX - limits.minX))
    val yLocation = limits.minY + (random.nextDouble() * (limits.maxY - limits.minY))
    Point2D(xLocation, yLocation)
  }

  @tailrec
  final def generatePoints(limits: Dimensions, num: Int, points: Seq[Point2D] = Seq()): Seq[Point2D] = {
    if (num > 0) {
      generatePoints(limits, num - 1, points :+ generatePoint(limits))
    } else {
      points
    }
  }
}

object Generator {

  def apply(seed: Int): Generator = new Generator(seed)

  def generatePoint()(implicit limits: Dimensions): Point2D = {
    val xLocation = limits.minX + (Random.nextDouble() * (limits.maxX - limits.minX))
    val yLocation = limits.minY + (Random.nextDouble() * (limits.maxY - limits.minY))
    Point2D(xLocation, yLocation)
  }

  def generatePoints(num: Int)(implicit limits: Dimensions): Seq[Point2D] = {
    Range(0, num).map(_ => generatePoint())
  }

}
