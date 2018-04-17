package ch.hugdata.island.map

import ch.hugdata.island.graph.threedee.{EdgeGraph3D, Node3D, Point3D, Polygon3D}
import ch.hugdata.island.svgwriter._
import ch.hugdata.island.{NodeProperties, PolygonProperties}
import com.softwaremill.tagging.@@

/**
  * Draws the calculated graph to a map
  */
final class MapDrawer(private val dimensions: Dimensions,
                      private val point3DPropertyAssembler: PropertyAssembler[Point3D],
                      private val polygon3DPropertyAssembler: PropertyAssembler[Polygon3D],
                      private val nodeProperties: Seq[Property] @@ NodeProperties,
                      private val polygonProperties: Seq[Property] @@ PolygonProperties) {

  private val backgroundProperties: Seq[Property] = Seq(Property.fill("white"))
  private val background = Rect(dimensions, backgroundProperties)

  def drawMap(edgeGraph3D: EdgeGraph3D): Group = {
    val nodeGroup: Group = nodesToSvg(edgeGraph3D.nodes)
    val polygonGroup: Group = polygonsToSvg(edgeGraph3D.polygons)
    new Group(Seq(background, polygonGroup, nodeGroup))
  }

  private def nodesToSvg(nodes: Seq[Node3D]): Group = {
    val svgColoredPoints = nodes
      .par
      .map(_.location)
      .map(point => (point, point3DPropertyAssembler.assembleProperties(point, nodeProperties)))
      .map(configuredPoint => Point(configuredPoint._1, configuredPoint._2))
      .seq
    new Group(svgColoredPoints)
  }

  private def polygonsToSvg(polygons: Seq[Polygon3D]): Group = {
    val svgColoredPolygons = polygons
      .map(poly => (poly, polygon3DPropertyAssembler.assembleProperties(poly, polygonProperties)))
      .map(configuredPoly => Polygon(configuredPoly._1, configuredPoly._2))
    new Group(svgColoredPolygons)
  }

}
