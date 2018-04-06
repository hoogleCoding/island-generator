package ch.hugdata.island.generator

import ch.hugdata.island.graph.Node
import ch.hugdata.island.map.Coordinate

import scala.util.Try

/**
  * Transforms a
  */
class IdentityCoordinateTransformer
  extends CoordinateTransformer {
  override def generate(point: Node): Try[Coordinate] = {
    Coordinate(point, 0)
  }
}
