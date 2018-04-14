package ch.hugdata.island.colorizer.quantifier

import ch.hugdata.island.MaxAltitude
import ch.hugdata.island.graph.threedee.Point3D
import com.softwaremill.tagging.@@

/**
  * Quantifies a point's elevation
  */
class PointElevationQuantifier(private val maxValue: Double @@ MaxAltitude)
  extends Quantifier[Point3D] {
  /**
    * Takes any property of an item and returns a value between 0 and 1
    *
    * @param point The item to quantify
    * @return A double value between 0.0 and 1.0
    */
  override def quantify(point: Point3D): Double = point.z / maxValue
}
