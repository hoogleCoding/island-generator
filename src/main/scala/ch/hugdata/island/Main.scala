package ch.hugdata.island

import ch.hugdata.island.graph._
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

    val seedPoints: Seq[Point2D] = generator.generatePoints(dimensions, 1000)

    val voronoiGraph = voronoiCalculator.iterateVoronoi(seedPoints, 4)
    val voronoiProperties = Seq(Property.stroke("black"), Property.strokeWith(2), Property.fill("none"))
    val voronoiGroup = new Group(voronoiGraph.polygons.map(Polygon.apply(_, voronoiProperties)))

    val edges: EdgeGraph = EdgeGenerator.generateEdges(voronoiGraph)
    val nodeProperties = Seq(Property.size(3))
    val svgColoredPoints = edges.nodes.par.map(coordinateTransformer.generate)
      .map(point => (point, point3DPropertyAssembler.assembleProperties(point, nodeProperties)))
      .map(configuredPoint => Point(configuredPoint._1, configuredPoint._2))
      .seq

    val nodeGroup: Group = new Group(svgColoredPoints)

    val updatedCenterProperties = Seq(Property.size(6), Property.fill("green"))
    val updatedCenters = new Group(getUpdatedCenters(voronoiGraph.polygons).map(Point(_, updatedCenterProperties)))

    val group = new Group(Seq(background, voronoiGroup, /* updatedCenters,*/ nodeGroup))
    svgWriter.writeToFile("/tmp/test.svg", group.toSVG().getOrElse(""))
  }

  def getUpdatedCenters(polygons: Seq[Polygon2D]): Seq[Point2D] = polygons.map(_.center)

}
