package ch.hugdata.island.generator

import ch.hugdata.island.graph.{Node, Point3D}

/**
  * Transforms a point from 2d into 3d space.
  */
class IdentityCoordinateTransformer
  extends CoordinateTransformer {
  override def generate(point: Node): Point3D = Point3D(point.x, point.y, 1)
}
