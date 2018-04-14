package ch.hugdata.island.graph

import ch.hugdata.island.graph.twodee.{Point2D, Polygon2D}

import scala.annotation.tailrec

/**
  * Converts a graph of polygons into a graph consisting only of triangles
  */
object Triangulator {

  def triangulate(polygons: Seq[Polygon2D]): Seq[Polygon2D] = {
    polygons.flatMap(poly => triangulate(poly.points))
  }

  /**
    * Triangulates a polygon, assuming the points are in order. This function does not check for crossing triangles.
    *
    * @param points    The points making up the polygon in order.
    * @param triangles The already calculated triangles.
    * @return The sequence of calculated triangles.
    */
  @tailrec
  def triangulate(points: Seq[Point2D], triangles: Seq[Polygon2D] = Seq.empty): Seq[Polygon2D] = points match {
    case _ :: _ :: Nil =>
      triangles
    case first :: second :: remaining =>
      triangulate(first :: remaining, triangles :+ new Polygon2D(Seq(first, second, remaining.head), Point2D(0, 0)))
  }

}
