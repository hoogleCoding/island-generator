package ch.hugdata.island.generator

import ch.hugdata.island.graph.threedee._
import ch.hugdata.island.graph.twodee.{Edge2D, EdgeGraph2D}
import ch.hugdata.island.graph.{Has2DLocation, Is2DPolygon}

/**
  * Transformer for [[Point3D]]s. Applies geographic mapping to add the elevation to a 2d point.
  */
trait CoordinateTransformer {

  def generate(edgeGraph: EdgeGraph2D): EdgeGraph3D

  protected def toEdge3D(edge2d: Edge2D, convertedPoints: Map[Has2DLocation, Node3D]): Option[Edge3D] = for {
    nodeA <- convertedPoints.get(edge2d.nodeA.location)
    nodeB <- convertedPoints.get(edge2d.nodeB.location)
  } yield Edge3D(nodeA, nodeB)

  protected def toPolygon3D(polygon: Is2DPolygon, convertedPoints: Map[Has2DLocation, Node3D]): Polygon3D = {
    val points = polygon.getPoints
      .flatMap(point => convertedPoints.get(point))
      .map(_.location)
    Polygon3D(points)
  }

}
