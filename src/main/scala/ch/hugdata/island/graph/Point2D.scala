package ch.hugdata.island.graph

import ch.hugdata.island.geography.Has2DLocation

/**
  * Type for 2D-Points
  */
case class Point2D(x: Double, y: Double)
  extends Has2DLocation {
  override def toString: String = s"Point2D($x, $y)"
}
