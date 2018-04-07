package ch.hugdata.island.colorizer.gradient

import ch.hugdata.island.svgwriter.Color

/**
  * Trait for gradient generation.
  */
trait Gradient {

  def getColor(value: Double): Option[Color]

}
