package ch.hugdata.island.graph.twodee

/**
  * Type for a site in a Voronoi graph
  */
case class Site(location: Point2D, edges: Seq[Edge2D])
