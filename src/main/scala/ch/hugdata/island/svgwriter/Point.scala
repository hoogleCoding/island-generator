package ch.hugdata.island.svgwriter

import ch.hugdata.island.graph.Point2D

import scala.util.{Failure, Success, Try}

/**
  * Represents an element in a svg-file
  */
case class Point(private val point: Point2D,
                 private val properties: Seq[Property] = Seq.empty)
  extends SvgElement {

  private def renderSvg(properties: Seq[Property]) = {
    s"""<circle cx="${point.xLocation}" cy="${point.yLocation}" ${properties.map(_.toSvg).mkString(" ")} />"""
  }

  override def toSVG()(implicit limits: Dimensions): Try[String] = {
    if (limits.withinLimits(point.xLocation, point.yLocation)) {
      Success(renderSvg(properties))
    } else {
      Failure(new Exception(s"The point $point is outside of the drawing area."))
    }
  }
}
