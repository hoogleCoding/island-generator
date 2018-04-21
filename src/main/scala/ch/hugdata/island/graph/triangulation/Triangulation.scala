package ch.hugdata.island.graph.triangulation

import ch.hugdata.island.graph.twodee.{Polygon2D, Triangle2D}

/**
  * Trait for polygon triangulation
  */
trait Triangulation {
  def triangulate(polygons: Seq[Polygon2D]): Seq[Triangle2D]

}
