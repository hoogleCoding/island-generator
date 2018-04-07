package ch.hugdata.island.graph

import ch.hugdata.island.geography.Has2DLocation

/**
  * Type for 3D-Points
  */
case class Point3D(override val x: Double, override val y: Double, z: Double)
  extends Has2DLocation
