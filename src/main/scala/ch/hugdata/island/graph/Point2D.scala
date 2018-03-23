package ch.hugdata.island.graph

/**
  * Type for 2D-Points
  */
case class Point2D(xLocation: Double, yLocation: Double) {
  override def toString: String = s"Point2D($xLocation, $yLocation)"
}
