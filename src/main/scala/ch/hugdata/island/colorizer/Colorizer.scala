package ch.hugdata.island.colorizer

import ch.hugdata.island.colorizer.gradient.Gradient
import ch.hugdata.island.colorizer.quantifier.Quantifier
import ch.hugdata.island.svgwriter.Color

/**
  * Takes a instance of Type and returns a color.
  */
trait Colorizer[Type] {

  def colorize(item: Type): Option[Color]

}

class ConcreteColorizer[Type](private val quantifier: Quantifier[Type], gradient: Gradient)
  extends Colorizer[Type] {
  override def colorize(item: Type): Option[Color] = gradient.getColor(quantifier.quantify(item))
}
