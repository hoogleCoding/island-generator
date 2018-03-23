package ch.hugdata.island.svgwriter

import scala.util.Try

/**
  * Trait for elements in a svg file
  */
trait SvgElement {
  def toSVG()(implicit limits: Dimensions): Try[String]
}
