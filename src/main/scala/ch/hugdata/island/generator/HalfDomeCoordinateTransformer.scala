package ch.hugdata.island.generator

import ch.hugdata.island.MaxAltitude
import ch.hugdata.island.graph.{Node, Point3D}
import ch.hugdata.island.svgwriter.Dimensions
import com.softwaremill.tagging.@@

/**
  * Maps point's height by applying a to-3d transformation based on a dome with its highest point at the center
  * of the map.
  */
class HalfDomeCoordinateTransformer(private val maxAltitude: Double @@ MaxAltitude,
                                    private val size: Dimensions)
  extends CoordinateTransformer {

  private val radius = (size.maxX - size.minX) / 2
  private val centerX = size.maxX - radius
  private val centerY = size.maxY - radius
  private val halfHeight = maxAltitude / 2

  override def generate(point: Node): Point3D = {
    val xSquaredDistance = math.pow(centerX - point.x, 2)
    val ySquaredDistance = math.pow(centerY - point.y, 2)
    val distanceFromCenter = math.sqrt(xSquaredDistance + ySquaredDistance)
    if (distanceFromCenter > radius) {
      Point3D(point.x, point.y, 0)
    } else {
      val zValue = math.cos(math.Pi * distanceFromCenter / radius) * halfHeight + halfHeight
      Point3D(point.x, point.y, zValue)
    }
  }
}
