package ch.hugdata.island.graph.threedee

import ch.hugdata.island.graph.Has2DLocation

/**
  * Type for 3D-Points
  */
case class Point3D(override val x: Double, override val y: Double, z: Double)
  extends Has2DLocation
