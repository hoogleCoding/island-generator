package ch.hugdata.island.graph

import ch.hugdata.island.geography.Has2DLocation

/**
  * Type for the creation of voronoi diagrams
  */
case class VoronoiGraph(sites: Seq[Has2DLocation],
                        polygons: Seq[Polygon2D])
