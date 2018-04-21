package ch.hugdata.island.graph.twodee

import ch.hugdata.island.graph.{Has2DLocation, Is2DPolygon}

/**
  * Type for a 2D triangle
  */
case class Triangle2D(pointA: Has2DLocation,
                      pointB: Has2DLocation,
                      pointC: Has2DLocation)
  extends Is2DPolygon {
  override val getPoints: Seq[Has2DLocation] = Seq(pointA, pointB, pointC)

  lazy val center = Point2D(
    (pointA.x + pointB.x + pointC.x) / 3,
    (pointA.y + pointB.y + pointC.y) / 3
  )
}
