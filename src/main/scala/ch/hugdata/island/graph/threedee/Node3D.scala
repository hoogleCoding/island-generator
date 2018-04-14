package ch.hugdata.island.graph.threedee

import ch.hugdata.island.graph.Has2DLocation

import scala.collection.mutable

/**
  * Type for nodes within the graph. Nodes combine the location, specified by a [[Point3D]] with the
  * set of [[Edge3D]]s connected to the location.
  */
case class Node3D(location: Point3D,
                  private val edges: mutable.Set[Edge3D] = mutable.Set.empty)
  extends Has2DLocation {
  def addEdge(edge: Edge3D): Boolean = {
    edges.add(edge)
  }

  override def hashCode(): Int = location.hashCode()

  override lazy val x: Double = location.x
  override lazy val y: Double = location.y
}
