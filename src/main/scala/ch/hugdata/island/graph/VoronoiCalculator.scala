package ch.hugdata.island.graph

import ch.hugdata.island.geography.Has2DLocation
import ch.hugdata.island.svgwriter.Dimensions
import kn.uni.voronoitreemap.datastructure.OpenList
import kn.uni.voronoitreemap.diagram.PowerDiagram
import kn.uni.voronoitreemap.j2d.PolygonSimple

import scala.annotation.tailrec
import scala.collection.JavaConverters._

/**
  * Calculator for the [[VoronoiGraph]] of a bunch of points.
  */
class VoronoiCalculator(private val limits: Dimensions) {

  /**
    * Iterates the Voronoi generation while using the updated cell centers as the new points thus achieving a more
    * even distribution of the points.
    *
    * @param points          the initial points to calculate the graph from.
    * @param numOfIterations The number of iteration for the calculation.
    * @return The last calculated voronoi graph.
    */
  def iterateVoronoi(points: Seq[Point2D], numOfIterations: Int): VoronoiGraph = {
    @tailrec
    def tailRecVoronoi(voronoiGraph: VoronoiGraph, numOfIterations: Int): VoronoiGraph = {
      if (numOfIterations <= 0) {
        voronoiGraph
      } else {
        val points = voronoiGraph.polygons.map(_.center)
        val updatedVoronoi = calculateVoronoi(points)
        tailRecVoronoi(updatedVoronoi, numOfIterations - 1)
      }
    }

    val voronoiGraph = calculateVoronoi(points)
    tailRecVoronoi(voronoiGraph, numOfIterations)
  }

  /**
    * Calculates the VoronoiGraph for a collection of points.
    *
    * @param points The points to calculate the graph from.
    * @return The voronoi graph calculated from the points.
    */
  def calculateVoronoi(points: Seq[Has2DLocation]): VoronoiGraph = {
    val rootPolygon = initializeRootPolygon(limits)
    val sites = convertPoints(points)
    val diagram = new PowerDiagram
    diagram.setSites(sites)
    diagram.setClipPoly(rootPolygon)
    diagram.computeDiagram()
    val polygons: Seq[Polygon2D] = sites.asScala
      .map(site => Polygon2D.fromPolygonSimple(site.getPolygon))
      .toSeq
    VoronoiGraph(points, polygons)
  }

  private def initializeRootPolygon(limits: Dimensions): PolygonSimple = {
    val rootPolygon = new PolygonSimple
    val width = 1000
    val height = 1000
    rootPolygon.add(0, 0)
    rootPolygon.add(width, 0)
    rootPolygon.add(width, height)
    rootPolygon.add(0, height)
    rootPolygon
  }

  private def convertPoints(points: Seq[Has2DLocation]): OpenList = {
    val sites: OpenList = new OpenList
    points.map(point => new kn.uni.voronoitreemap.j2d.Site(point.x, point.y))
      .foreach(sites.add)
    sites
  }

}
