package ch.hugdata.island.graph.twodee

import ch.hugdata.island.graph.Edge

/**
  * Type for an edge between two [[Node2D]]s.
  */
case class Edge2D(nodeA: Node2D, nodeB: Node2D)
  extends Edge {
  override def length: Double = math.sqrt(math.pow(nodeA.x - nodeB.x, 2) + math.pow(nodeA.y - nodeB.y, 2))
}

//TODO: implement ordering of nodes

