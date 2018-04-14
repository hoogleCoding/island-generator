package ch.hugdata.island.graph

import ch.hugdata.island.graph.twodee._

import scala.collection.mutable

/**
  * Created by Florian Hug <florian.hug@gmail.com> on 3/26/18.
  */
object EdgeGenerator {

  def generateEdges(voronoiGraph: VoronoiGraph): EdgeGraph2D = {
    implicit val pointMap: mutable.HashMap[Point2D, Node2D] = mutable.HashMap[Point2D, Node2D]()

    val sites: Seq[Site] = voronoiGraph.polygons.map(poly => Site(poly.center, polygonToEdges(poly)))
    val edges: Seq[Edge2D] = sites.flatMap(_.edges).distinct

    EdgeGraph2D(
      edges,
      pointMap.values.toSeq,
      sites,
      voronoiGraph.polygons)
  }

  private def polygonToEdges(polygon2D: Polygon2D)(implicit pointMap: mutable.HashMap[Point2D, Node2D]): Seq[Edge2D] = {
    val points: Seq[(Point2D, Point2D)] = polygon2D.points
      .sliding(2)
      .map(se => (se.head, se.last))
      .toSeq :+ ((polygon2D.points.last, polygon2D.points.head))
    points.map(pair => generateEdge(pair._1, pair._2)(pointMap))
  }

  private def generateEdge(a: Point2D, b: Point2D)(pointMap: mutable.HashMap[Point2D, Node2D]) = {
    val nodeA: Node2D = pointMap.getOrElseUpdate(a, Node2D(a))
    val nodeB: Node2D = pointMap.getOrElseUpdate(b, Node2D(b))
    val edge = Edge2D(nodeA, nodeB)
    nodeA.addEdge(edge)
    nodeB.addEdge(edge)
    edge
  }
}
