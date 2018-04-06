package ch.hugdata.island.graph

import ch.hugdata.island.geography.Has2DLocation

import scala.collection.mutable

/**
  * Type for nodes within the graph. Nodes combine the location, specified by a [[Point2D]] with the
  * set of [[Edge]]s connected to the location.
  */
case class Node(location: Point2D,
                private val edges: mutable.Set[Edge] = mutable.Set.empty)
  extends Has2DLocation {
  def addEdge(edge: Edge): Boolean = {
    edges.add(edge)
  }

  override def hashCode(): Int = location.hashCode()

  override lazy val x: Double = location.x
  override lazy val y: Double = location.y
}
