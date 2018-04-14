package ch.hugdata.island.graph.twodee

import ch.hugdata.island.graph.Has2DLocation

import scala.collection.mutable

/**
  * Type for nodes within the graph. Nodes combine the location, specified by a [[Point2D]] with the
  * set of [[Edge2D]]s connected to the location.
  */
case class Node2D(location: Has2DLocation,
                  private val edges: mutable.Set[Edge2D] = mutable.Set.empty)
  extends Has2DLocation {
  def addEdge(edge: Edge2D): Boolean = {
    edges.add(edge)
  }

  override def hashCode(): Int = location.hashCode()

  override lazy val x: Double = location.x
  override lazy val y: Double = location.y
}
