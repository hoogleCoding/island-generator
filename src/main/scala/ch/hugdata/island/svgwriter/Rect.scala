package ch.hugdata.island.svgwriter

import scala.util.{Failure, Success, Try}

/**
  * Type for a rectangle in svg
  */
case class Rect(private val dimensions: Dimensions,
                private val properties: Seq[Property] = Seq.empty)
  extends SvgElement {

  private def renderSvg(properties: Seq[Property]) = {
    s"""<rect cx="${dimensions.minX}" cy="${dimensions.minY}" width="${dimensions.maxX - dimensions.minX}" height="${dimensions.maxY - dimensions.minY}" ${properties.map(_.toSvg).mkString(" ")} />"""
  }

  override def toSVG()(implicit limits: Dimensions): Try[String] = {
    if (limits.withinLimits(dimensions)) {
      Success(renderSvg(properties))
    } else {
      Failure(new Exception(s"The rectangle $dimensions is outside of the drawing area."))
    }
  }
}
