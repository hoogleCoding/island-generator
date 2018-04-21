package ch.hugdata.island.graph.twodee

import ch.hugdata.island.graph.Is2DPolygon

/**
  * Type for representing the fully connected graph.
  */
case class EdgeGraph2D(edges: Seq[Edge2D],
                       nodes: Seq[Node2D],
                       sites: Seq[Site],
                       polygons: Seq[Is2DPolygon])
