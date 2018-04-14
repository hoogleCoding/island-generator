package ch.hugdata.island.svgwriter

import ch.hugdata.island.graph.Has2DLocation

import scala.util.{Failure, Success, Try}

/**
  * Represents an element in a svg-file
  */
case class Point(private val point: Has2DLocation,
                 private val properties: Seq[Property] = Seq.empty)
  extends SvgElement {

  private def renderSvg(properties: Seq[Property]) = {
    s"""<circle cx="${point.x}" cy="${point.y}" ${properties.map(_.toSvg).mkString(" ")} />"""
  }

  override def toSVG()(implicit limits: Dimensions): Try[String] = {
    if (limits.withinLimits(point.x, point.y)) {
      Success(renderSvg(properties))
    } else {
      Failure(new Exception(s"The point $point is outside of the drawing area."))
    }
  }
}
