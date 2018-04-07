package ch.hugdata.island.colorizer.gradient

import ch.hugdata.island.svgwriter.Color

import scala.util.{Failure, Success, Try}

/**
  * Fix point on a [[ColorGradient]]
  */
class ColorFixPoint private(val value: Double, val color: Color)

object ColorFixPoint {
  def apply(value: Double, color: Color): Try[ColorFixPoint] = {
    if (value >= 0 && value <= 1.0) {
      Success(new ColorFixPoint(value, color))
    } else {
      Failure(new ColorException(s"Gradient fix point $value is out of bounds (0 ,1)"))
    }
  }
}
