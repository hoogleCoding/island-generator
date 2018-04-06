package ch.hugdata.island.graph

/**
  * Type for representing the fully connected graph.
  */
case class EdgeGraph(edges: Seq[Edge], nodes: Seq[Node], sites: Seq[Site])
