package ch.hugdata.island.generator

import ch.hugdata.island.graph.Node
import ch.hugdata.island.map.Coordinate

import scala.util.Try

/**
  * Transformer for [[ch.hugdata.island.map.Coordinate]]s. Applies geographic mapping to add the elevation to a 2d point.
  */
trait CoordinateTransformer {

  def generate(point: Node): Try[Coordinate]

}
