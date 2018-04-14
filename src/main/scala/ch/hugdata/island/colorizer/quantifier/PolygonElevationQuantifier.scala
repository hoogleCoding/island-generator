package ch.hugdata.island.colorizer.quantifier

import ch.hugdata.island.MaxAltitude
import ch.hugdata.island.graph.threedee.Polygon3D
import com.softwaremill.tagging.@@

/**
  * Quantifies a polygon's average elevation
  */
class PolygonElevationQuantifier(private val maxValue: Double @@ MaxAltitude)
  extends Quantifier[Polygon3D] {
  /**
    * Takes any property of an item and returns a value between 0 and 1
    *
    * @param polygon The item to quantify
    * @return A double value between 0.0 and 1.0
    */
  override def quantify(polygon: Polygon3D): Double = {
    polygon.averageHeight / maxValue
  }
}
