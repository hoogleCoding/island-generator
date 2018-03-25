package ch.hugdata.island.graph

/**
  * Type for a site in a Voronoi graph
  */
case class Site(location: Point2D, edges: Seq[Edge])
