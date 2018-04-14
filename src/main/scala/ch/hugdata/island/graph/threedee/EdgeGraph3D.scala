package ch.hugdata.island.graph.threedee

/**
  * Type for representing the fully connected graph.
  */
case class EdgeGraph3D(edges: Seq[Edge3D],
                     nodes: Seq[Node3D],
                     polygons: Seq[Polygon3D])
