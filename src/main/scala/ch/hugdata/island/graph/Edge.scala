package ch.hugdata.island.graph

import scala.collection.mutable

case class EdgeGraph(edges: Seq[Edge], nodes: Seq[Node], sites: Seq[Site])

/**
  * Type for an edge between two points.
  */
case class Edge(nodeA: Node, nodeB: Node)

//TODO: implement ordering of nodes

case class Node(location: Point2D,
                private val edges: mutable.Set[Edge] = mutable.Set.empty) {
  def addEdge(edge: Edge): Boolean = {
    edges.add(edge)
  }

  override def hashCode(): Int = location.hashCode()
}

object EdgeGenerator {

  def generateEdges(voronoiGraph: VoronoiGraph): EdgeGraph = {
    implicit val pointMap: mutable.HashMap[Point2D, Node] = mutable.HashMap[Point2D, Node]()

    val sites: Seq[Site] = voronoiGraph.polygons.map(poly => Site(poly.center, polygonToEdges(poly)))
    val edges: Seq[Edge] = sites.flatMap(_.edges).distinct

    EdgeGraph(
      edges,
      pointMap.values.toSeq,
      sites)
  }

  private def polygonToEdges(polygon2D: Polygon2D)(implicit pointMap: mutable.HashMap[Point2D, Node]): Seq[Edge] = {
    val points: Seq[(Point2D, Point2D)] = polygon2D.points
      .sliding(2)
      .map(se => (se.head, se.last))
      .toSeq :+ ((polygon2D.points.last, polygon2D.points.head))
    points.map(pair => generateEdge(pair._1, pair._2)(pointMap))
  }

  private def generateEdge(a: Point2D, b: Point2D)(pointMap: mutable.HashMap[Point2D, Node]) = {
    val nodeA: Node = pointMap.getOrElseUpdate(a, Node(a))
    val nodeB: Node = pointMap.getOrElseUpdate(b, Node(b))
    val edge = Edge(nodeA, nodeB)
    nodeA.addEdge(edge)
    nodeB.addEdge(edge)
    edge
  }
}