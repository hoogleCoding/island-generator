package ch.hugdata.island

import ch.hugdata.island.generator.Generator
import ch.hugdata.island.graph.{Point2D, Polygon2D, VoronoiDiagram}
import ch.hugdata.island.svgwriter._

/**
  * Created by Florian Hug <florian.hug@gmail.com> on 3/19/18.
  */
object Main {


  def main(args: Array[String]): Unit = {
    implicit val dimensions: Dimensions = Dimensions(0, 1000, 0, 1000)

    val backgroundProperties: Seq[Property] = Seq(Property.fill("white"))
    val background = Rect(dimensions, backgroundProperties)

    val seedPoints: Seq[Point2D] = Generator.generatePoints(500)

    val voronoiDiagram = iterateVoronoi(seedPoints, dimensions, 4)
    val voronoiProperties = Seq(Property.stroke("black"), Property.strokeWith(2), Property.fill("none"))
    val voronoiGroup = new Group(voronoiDiagram.polygons.map(Polygon.apply(_, voronoiProperties)))

    val updatedCenterProperties = Seq(Property.size(6), Property.fill("green"))
    val updatedCenters = new Group(getUpdatedCenters(voronoiDiagram.polygons).map(Point(_, updatedCenterProperties)))

    val group = new Group(Seq(background, voronoiGroup, updatedCenters))
    val svgWriter = new SvgWriter
    svgWriter.writeToFile("/tmp/test.svg", group.toSVG().getOrElse(""))
  }

  def getUpdatedCenters(polygons: Seq[Polygon2D]): Seq[Point2D] = polygons.map(_.center)

  def iterateVoronoi(points: Seq[Point2D], limits: Dimensions, numOfIterations: Int): VoronoiDiagram = {
    if (numOfIterations <= 0) {
      VoronoiDiagram.calculateVoronoi(points, limits)
    } else {
      val updatedPoints = iterateVoronoi(points, limits, numOfIterations - 1)
        .polygons
        .map(_.center)
      VoronoiDiagram.calculateVoronoi(updatedPoints, limits)
    }
  }

}
