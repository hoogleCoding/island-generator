package ch.hugdata.island.graph

import ch.hugdata.island.graph.triangulation.Triangulation
import ch.hugdata.island.graph.twodee._

import scala.collection.mutable

/**
  * Generates edges from [VoronoiGraph]s
  */
class EdgeGenerator(private val triangulation: Triangulation) {

  def generateEdges(voronoiGraph: VoronoiGraph): EdgeGraph2D = {
    implicit val pointMap: mutable.HashMap[Has2DLocation, Node2D] = mutable.HashMap[Has2DLocation, Node2D]()

    val triangles: Seq[Triangle2D] = triangulation.triangulate(voronoiGraph.polygons)
    val sites = triangles.map(triangle => Site(triangle.center, polygonToEdges(triangle)))

    //val sites: Seq[Site] = voronoiGraph.polygons.map(poly => Site(poly.center, polygonToEdges(poly)))
    val edges: Seq[Edge2D] = sites.flatMap(_.edges).distinct

    EdgeGraph2D(
      edges,
      pointMap.values.toSeq,
      sites,
      triangles)
  }

  private def polygonToEdges(polygon: Is2DPolygon)(implicit pointMap: mutable.HashMap[Has2DLocation, Node2D]): Seq[Edge2D] = {
    val points: Seq[(Has2DLocation, Has2DLocation)] = polygon.getPoints
      .sliding(2)
      .map(se => (se.head, se.last))
      .toSeq :+ ((polygon.getPoints.last, polygon.getPoints.head))
    points.map(pair => generateEdge(pair._1, pair._2)(pointMap))
  }

  private def generateEdge(a: Has2DLocation, b: Has2DLocation)(pointMap: mutable.HashMap[Has2DLocation, Node2D]) = {
    val nodeA: Node2D = pointMap.getOrElseUpdate(a, Node2D(a))
    val nodeB: Node2D = pointMap.getOrElseUpdate(b, Node2D(b))
    val edge = Edge2D(nodeA, nodeB)
    nodeA.addEdge(edge)
    nodeB.addEdge(edge)
    edge
  }
}
