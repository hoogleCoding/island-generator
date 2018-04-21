package ch.hugdata.island.graph.triangulation

import ch.hugdata.island.graph.twodee.{Point2D, Polygon2D, Triangle2D}

import scala.annotation.tailrec

/**
  * Triangulates a polygon by cutting off the most acute 'ear'.
  */
class MostAcuteCornerTriangulation
  extends Triangulation {
  override def triangulate(polygons: Seq[Polygon2D]): Seq[Triangle2D] = {
    polygons.par
      .flatMap(triangulate(_))
      .seq
  }

  private def isTriangle(polygon: Polygon2D) = {
    polygon.getPoints.length == 3
  }

  @tailrec
  private def triangulate(polygon: Polygon2D, triangles: Seq[Triangle2D] = Seq.empty): Seq[Triangle2D] = polygon match {
    case _ if polygon.points.length < 2 => triangles
    case poly if isTriangle(poly) => triangles :+ Triangle2D(poly.points.head,
      poly.points(1),
      poly.points(2))
    case poly =>
      val pointList = poly.points.toList
      val mostAcute = calculateAngles(pointList)
        .minBy(ang => ang._1)
        ._2
      val updatedTriangles = triangles :+ Triangle2D(mostAcute._1, mostAcute._2, mostAcute._3)
      val updatedPointList = pointList.filterNot(item => item.equals(mostAcute._2))
      triangulate(Polygon2D(updatedPointList, polygon.center), updatedTriangles)

  }

  private def calculateAngles(points: List[Point2D]): Map[Double, (Point2D, Point2D, Point2D)] = {
    val appendedList = points :+ points.head :+ points(1)
    appendedList.sliding(3)
      .map(ps => calculateAngle(ps.head, ps(1), ps(2)) -> (ps.head, ps(1), ps(2)))
      .toMap
  }

  /**
    * Calculates the angle between three points where pointB is the point at which the angle will be calculated.
    *
    * @return
    */
  private def calculateAngle(pointA: Point2D, pointB: Point2D, pointC: Point2D): Double = {
    val vectorBA = Vector(math.abs(pointA.x - pointB.x),
      math.abs(pointA.y - pointB.y))
    val vectorBC = Vector(math.abs(pointC.x - pointB.x),
      math.abs(pointC.y - pointB.y))
    val angle = dotProduct(vectorBA, vectorBC) / (vectorBA.length * vectorBC.length)
    math.acos(angle)

  }

  case class Vector(xDir: Double, yDir: Double) {
    lazy val length: Double = math.sqrt(math.pow(xDir, 2) + math.pow(yDir, 2))
  }

  private def dotProduct(vector1: Vector, vector2: Vector) = vector1.xDir * vector2.xDir + vector1.yDir * vector2.yDir
}
