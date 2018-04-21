package ch.hugdata.island.graph.threedee

import ch.hugdata.island.graph.Edge

/**
  * Type for an edge between two [[Node3D]]s.
  */
case class Edge3D(nodeA: Node3D, nodeB: Node3D)
  extends Edge {
  override def length: Double = math.sqrt(
    math.pow(nodeA.location.x - nodeB.location.x, 2)
      + math.pow(nodeA.location.y - nodeB.location.y, 2)
      + math.pow(nodeA.location.z - nodeB.location.z, 2)
  )
}

//TODO: implement ordering of nodes

