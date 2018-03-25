package ch.hugdata.island.svgwriter

import ch.hugdata.island.graph.{Point2D, Polygon2D}

import scala.util.{Failure, Success, Try}

/**
  * Type for SVG-polygons
  */
case class Polygon(private val polygon: Polygon2D,
                   private val properties: Seq[Property] = Seq.empty)
  extends SvgElement {

  private def renderSvg(points: Seq[Point2D]) = {
    def serialize(point: Point2D): String = s"${point.xLocation},${point.yLocation}"
    s"""<polygon points="${points.map(serialize).mkString(" ")}" ${properties.map(_.toSvg).mkString(" ")} />"""
  }

  override def toSVG()(implicit limits: Dimensions): Try[String] = {
    if (polygon.points.forall(point => limits.withinLimits(point.xLocation, point.yLocation))) {
      Success(renderSvg(polygon.points))
    } else {
      Failure(new Exception("At least part of the polygon is out of limits."))
    }
  }
}
