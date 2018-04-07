package ch.hugdata.island.generator

import ch.hugdata.island.graph.{Node, Point3D}

/**
  * Transformer for [[ch.hugdata.island.graph.Point3D]]s. Applies geographic mapping to add the elevation to a 2d point.
  */
trait CoordinateTransformer {

  def generate(point: Node): Point3D

}
