package ch.hugdata.island

import ch.hugdata.island.generator.Generator
import ch.hugdata.island.graph._
import ch.hugdata.island.svgwriter._

import scala.annotation.tailrec

/**
  * Created by Florian Hug <florian.hug@gmail.com> on 3/19/18.
  */
object Main {


  def main(args: Array[String]): Unit = {
    implicit val dimensions: Dimensions = Dimensions(0, 1000, 0, 1000)
    val generator = new Generator(73)

    val backgroundProperties: Seq[Property] = Seq(Property.fill("white"))
    val background = Rect(dimensions, backgroundProperties)

    val seedPoints: Seq[Point2D] = generator.generatePoints(dimensions, 1000)

    val voronoiGraph = iterateVoronoi(seedPoints, dimensions, 4)
    val voronoiProperties = Seq(Property.stroke("black"), Property.strokeWith(2), Property.fill("none"))
    val voronoiGroup = new Group(voronoiGraph.polygons.map(Polygon.apply(_, voronoiProperties)))

    val edges: EdgeGraph = EdgeGenerator.generateEdges(voronoiGraph)
    val nodeProperties = Seq(Property.size(3), Property.fill("red"))
    val nodeGroup = new Group(edges.nodes.map(point => Point(point.location, nodeProperties)))

    val updatedCenterProperties = Seq(Property.size(6), Property.fill("green"))
    val updatedCenters = new Group(getUpdatedCenters(voronoiGraph.polygons).map(Point(_, updatedCenterProperties)))

    val group = new Group(Seq(background, voronoiGroup, updatedCenters, nodeGroup))
    val svgWriter = new SvgWriter
    svgWriter.writeToFile("/tmp/test.svg", group.toSVG().getOrElse(""))
  }

  def getUpdatedCenters(polygons: Seq[Polygon2D]): Seq[Point2D] = polygons.map(_.center)

  def iterateVoronoi(points: Seq[Point2D], limits: Dimensions, numOfIterations: Int): VoronoiGraph = {
    @tailrec
    def tailRecVoronoi(voronoiGraph: VoronoiGraph, limits: Dimensions, numOfIterations: Int): VoronoiGraph = {
      if (numOfIterations <= 0) {
        voronoiGraph
      } else {
        val points = voronoiGraph.polygons.map(_.center)
        val updatedVoronoi = VoronoiGraph.calculateVoronoi(points, limits)
        tailRecVoronoi(updatedVoronoi, limits, numOfIterations - 1)
      }
    }

    val voronoiGraph = VoronoiGraph.calculateVoronoi(points, limits)
    tailRecVoronoi(voronoiGraph, limits, numOfIterations)
  }

}
