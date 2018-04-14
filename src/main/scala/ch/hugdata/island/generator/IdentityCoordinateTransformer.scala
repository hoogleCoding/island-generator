package ch.hugdata.island.generator

import ch.hugdata.island.graph.Has2DLocation
import ch.hugdata.island.graph.threedee._
import ch.hugdata.island.graph.twodee._

/**
  * Transforms a point from 2d into 3d space.
  */
class IdentityCoordinateTransformer
  extends CoordinateTransformer {
  override def generate(edgeGraph: EdgeGraph2D): EdgeGraph3D = {
    val points: Map[Has2DLocation, Node3D] = edgeGraph.nodes
      .map(node => node.location -> Point3D(node.x, node.y, 1))
      .toMap
      .mapValues(Node3D(_))
    val edges: Seq[Edge3D] = edgeGraph.edges
      .flatMap(edge => toEdge3D(edge, points))
    edges.foreach(edge => {
      edge.nodeA.addEdge(edge)
      edge.nodeB.addEdge(edge)
    })
    val polygons: Seq[Polygon3D] = edgeGraph.polygons.map(poly => toPolygon3D(poly, points))
    EdgeGraph3D(edges, points.values.toSeq, polygons)
  }
}
