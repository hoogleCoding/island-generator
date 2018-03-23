package ch.hugdata.island.graph

import ch.hugdata.island.svgwriter.Dimensions
import kn.uni.voronoitreemap.datastructure.OpenList
import kn.uni.voronoitreemap.diagram.PowerDiagram
import kn.uni.voronoitreemap.j2d.{PolygonSimple, Site}

import scala.collection.JavaConverters._


/**
  * Type for the creation of voronoi diagrams
  */
case class VoronoiDiagram(polygons: Seq[Polygon2D])

object VoronoiDiagram {
  def calculateVoronoi(points: Seq[Point2D], limits: Dimensions): VoronoiDiagram = {
    val rootPolygon = initializeRootPolygon(limits)
    val sites = convertPoints(points)
    val diagram = new PowerDiagram
    diagram.setSites(sites)
    diagram.setClipPoly(rootPolygon)
    diagram.computeDiagram()
    val polygons: Seq[Polygon2D] = sites.asScala
      .map(site => Polygon2D.fromPolygonSimple(site.getPolygon))
      .toSeq
    VoronoiDiagram(polygons)
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

  private def convertPoints(points: Seq[Point2D]): OpenList = {
    val sites: OpenList = new OpenList
    points.map(point => new Site(point.xLocation, point.yLocation))
      .foreach(sites.add)
    sites
  }
}
