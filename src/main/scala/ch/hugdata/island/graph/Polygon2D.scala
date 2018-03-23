package ch.hugdata.island.graph

import kn.uni.voronoitreemap.j2d.PolygonSimple

import scala.collection.JavaConverters._

/**
  * Type for 2-dimensional polygons
  */
case class Polygon2D(points: Seq[Point2D], center: Point2D)

object Polygon2D {
  def fromPolygonSimple(simplePolygon: PolygonSimple): Polygon2D = {
    Polygon2D(simplePolygon.asScala
      .map(point => Point2D(point.x, point.y))
      .toSeq,
      getPoint2D(simplePolygon.getCentroid))
  }

  private def getPoint2D(point2D: kn.uni.voronoitreemap.j2d.Point2D) =
    Point2D(point2D.x, point2D.y)
}
