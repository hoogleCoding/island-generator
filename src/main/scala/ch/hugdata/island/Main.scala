package ch.hugdata.island

import ch.hugdata.island.graph._
import ch.hugdata.island.graph.twodee.{EdgeGraph2D, Point2D, Polygon2D}
import ch.hugdata.island.svgwriter._

/**
  * Created by Florian Hug <florian.hug@gmail.com> on 3/19/18.
  */
object Main extends IslandModule {

  override val seed = 73
  override implicit val dimensions: Dimensions = Dimensions(0, 1000, 0, 1000)

  def main(args: Array[String]): Unit = {

    val backgroundProperties: Seq[Property] = Seq(Property.fill("white"))
    val background = Rect(dimensions, backgroundProperties)

    val seedPoints: Seq[Point2D] = generator.generatePoints(dimensions, 2000)

    val voronoiGraph = voronoiCalculator.iterateVoronoi(seedPoints, 4)
    val voronoiProperties = Seq(Property.stroke("black"), Property.strokeWith(2), Property.fill("none"))
    val polygons = new Group(voronoiGraph.polygons.map(Polygon.apply(_, voronoiProperties)))

    val edges: EdgeGraph2D = EdgeGenerator.generateEdges(voronoiGraph)
    val nodeProperties = Seq(Property.size(3))
    val edgeGraph3D = coordinateTransformer.generate(edges)
    val svgColoredPoints = edgeGraph3D.nodes
      .par
      .map(_.location)
      .map(point => (point, point3DPropertyAssembler.assembleProperties(point, nodeProperties)))
      .map(configuredPoint => Point(configuredPoint._1, configuredPoint._2))
      .seq
    val nodeGroup: Group = new Group(svgColoredPoints)

    val polygonProperties = Seq(Property.strokeWith(0))
    val svgColoredPolygons = edgeGraph3D.polygons
      .map(poly => (poly, polygon3DPropertyAssembler.assembleProperties(poly,polygonProperties)))
      .map(configuredPoly => Polygon(configuredPoly._1,configuredPoly._2))
    val polygonGroup = new Group(svgColoredPolygons)

    val updatedCenterProperties = Seq(Property.size(6), Property.fill("green"))
    val updatedCenters = new Group(getUpdatedCenters(voronoiGraph.polygons).map(Point(_, updatedCenterProperties)))

    val group = new Group(Seq(background, polygonGroup, /* updatedCenters,*/ nodeGroup))
    svgWriter.writeToFile("/tmp/test.svg", group.toSVG().getOrElse(""))
  }

  def getUpdatedCenters(polygons: Seq[Polygon2D]): Seq[Point2D] = polygons.map(_.center)

}
