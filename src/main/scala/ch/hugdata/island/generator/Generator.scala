package ch.hugdata.island.generator

import ch.hugdata.island.graph.Point2D
import ch.hugdata.island.svgwriter.Dimensions

import scala.util.Random

/**
  * Created by Florian Hug <florian.hug@gmail.com> on 3/19/18.
  */
object Generator {

  def generatePoint()(implicit limits: Dimensions): Point2D = {
    val xLocation = limits.minX + (Random.nextDouble() * (limits.maxX - limits.minX))
    val yLocation = limits.minY + (Random.nextDouble() * (limits.maxY - limits.minY))
    Point2D(xLocation, yLocation)
  }

  def generatePoints(num: Int)(implicit limits: Dimensions): Seq[Point2D] = {
    Range(0, num).map(_ => generatePoint())
  }

}
