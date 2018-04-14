package ch.hugdata.island.svgwriter

import ch.hugdata.island.graph.{Has2DLocation, Is2DPolygon}

import scala.util.{Failure, Success, Try}

/**
  * Type for SVG-polygons
  */
case class Polygon(private val polygon: Is2DPolygon,
                   private val properties: Seq[Property] = Seq.empty)
  extends SvgElement {

  private def renderSvg(points: Seq[Has2DLocation]) = {
    def serialize(point: Has2DLocation): String = s"${point.x},${point.y}"
    s"""<polygon points="${points.map(serialize).mkString(" ")}" ${properties.map(_.toSvg).mkString(" ")} />"""
  }

  override def toSVG()(implicit limits: Dimensions): Try[String] = {
    if (polygon.getPoints.forall(point => limits.withinLimits(point.x, point.y))) {
      Success(renderSvg(polygon.getPoints))
    } else {
      Failure(new Exception("At least part of the polygon is out of limits."))
    }
  }
}
