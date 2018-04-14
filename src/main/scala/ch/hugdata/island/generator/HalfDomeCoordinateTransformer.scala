package ch.hugdata.island.generator

import ch.hugdata.island.MaxAltitude
import ch.hugdata.island.graph.Has2DLocation
import ch.hugdata.island.graph.threedee._
import ch.hugdata.island.graph.twodee.{EdgeGraph2D, Node2D}
import ch.hugdata.island.svgwriter.Dimensions
import com.softwaremill.tagging.@@

/**
  * Maps point's height by applying a to-3d transformation based on a dome with its highest point at the center
  * of the map.
  */
class HalfDomeCoordinateTransformer(private val maxAltitude: Double @@ MaxAltitude,
                                    private val size: Dimensions)
  extends CoordinateTransformer {

  private val radius = (size.maxX - size.minX) / 2
  private val centerX = size.maxX - radius
  private val centerY = size.maxY - radius
  private val halfHeight = maxAltitude / 2

  private def mapPoint(point: Node2D): Point3D = {
    val xSquaredDistance = math.pow(centerX - point.x, 2)
    val ySquaredDistance = math.pow(centerY - point.y, 2)
    val distanceFromCenter = math.sqrt(xSquaredDistance + ySquaredDistance)
    if (distanceFromCenter > radius) {
      Point3D(point.x, point.y, 0)
    } else {
      val zValue = math.cos(math.Pi * distanceFromCenter / radius) * halfHeight + halfHeight
      Point3D(point.x, point.y, zValue)
    }
  }

  override def generate(edgeGraph: EdgeGraph2D): EdgeGraph3D = {
    val mappedPoints: Map[Has2DLocation, Node3D] = edgeGraph.nodes
      .map(node => node.location -> mapPoint(node))
      .toMap
      .mapValues(Node3D(_))
    val edges: Seq[Edge3D] = edgeGraph.edges
      .flatMap(edge => toEdge3D(edge, mappedPoints))
    edges.foreach(edge => {
      edge.nodeA.addEdge(edge)
      edge.nodeB.addEdge(edge)
    })
    val polygons: Seq[Polygon3D] = edgeGraph.polygons.map(poly => toPolygon3D(poly, mappedPoints))
    EdgeGraph3D(edges, mappedPoints.values.toSeq, polygons)
  }
}
