package ch.hugdata.island.graph

import ch.hugdata.island.graph.twodee.Polygon2D

/**
  * Type for the creation of voronoi diagrams
  */
case class VoronoiGraph(sites: Seq[Has2DLocation],
                        polygons: Seq[Polygon2D])
