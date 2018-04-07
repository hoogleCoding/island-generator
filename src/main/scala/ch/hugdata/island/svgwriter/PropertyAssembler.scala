package ch.hugdata.island.svgwriter

import ch.hugdata.island.colorizer.Colorizer

/**
  * Assembles properties from different sources into one property list.
  */
class PropertyAssembler[Type](colorizer: Colorizer[Type]) {

  def assembleProperties(item: Type, otherProperties: Seq[Property]): Seq[Property] =
    colorizer.colorize(item)
      .map(_.toSVG)
      .map(Property.fill)
      .map(_ +: otherProperties)
      .getOrElse(otherProperties)

}
