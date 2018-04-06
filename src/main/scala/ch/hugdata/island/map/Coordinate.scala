package ch.hugdata.island.map

import ch.hugdata.island.geography.Has2DLocation

import scala.util.{Failure, Success, Try}

/**
  * Type for coordinate with x and y location plus an elevation associated with it.
  */
class Coordinate(location: Has2DLocation,
                 elevation: Double)
  extends Has2DLocation {
  override val x: Double = location.x
  override val y: Double = location.y
}

object Coordinate {
  def apply(location: Has2DLocation, elevation: Double): Try[Coordinate] = {
    if (location.x >= 0 && location.y >= 0 && elevation >= 0) {
      Success(new Coordinate(location, elevation))
    } else {
      Failure(GeographyException("Parameters for Coordinate out of bounds"))
    }
  }
}
