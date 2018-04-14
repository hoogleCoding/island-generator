package ch.hugdata.island.graph.threedee

import ch.hugdata.island.graph.{Has2DLocation, Is2DPolygon}

/**
  * Type for 3D-Polygons
  */
case class Polygon3D(points: Seq[Point3D])
  extends Is2DPolygon {
  override def getPoints: Seq[Has2DLocation] = points

  /**
    * Get the average height of the polygon. This just the average of height of the points which make up the polygon.
    *
    * @return The average height of the polygon
    */
  lazy val averageHeight: Double = points
    .map(_.z)
    .sum / points.length
}
