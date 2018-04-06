package ch.hugdata.island

import ch.hugdata.island.geography.Has2DLocation
import ch.hugdata.island.graph._
import ch.hugdata.island.svgwriter._
import ch.hugdata.island.svgwriter.gradient.ColorGradient

import scala.util.Try

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
    val nodeProperties = Seq(Property.size(3), Property.fill("red"))
    val nodeGroup: Try[Group] = gradient.map(colorPointsByBorderDistance(edges.nodes,dimensions,_)).map(new Group(_))

    val updatedCenterProperties = Seq(Property.size(6), Property.fill("green"))
    val updatedCenters = new Group(getUpdatedCenters(voronoiGraph.polygons).map(Point(_, updatedCenterProperties)))

    val group = new Group(Seq(background, voronoiGroup, /* updatedCenters,*/ nodeGroup.get))
    svgWriter.writeToFile("/tmp/test.svg", group.toSVG().getOrElse(""))
  }

  def getUpdatedCenters(polygons: Seq[Polygon2D]): Seq[Point2D] = polygons.map(_.center)

  def colorPointsByBorderDistance(points: Seq[Has2DLocation],
                                  limits: Dimensions,
                                  gradient: ColorGradient): Seq[Point] = {
    val pointsWithDistance: Seq[(Has2DLocation, Double)] = points.map(point => (point, minimalDistanceFromBorder(point, limits)))
    val maxValue = pointsWithDistance.map(distances => distances._2).max

    pointsWithDistance
      .map(item => (item._1, item._2 / maxValue))
      .map(item => (item._1, gradient.getColor(item._2).getOrElse(Color.black)))
      .map { item =>
        val nodeProperties = Seq(Property.size(6), Property.fill(item._2.toSVG))
        Point(item._1, nodeProperties)
      }
  }

  def minimalDistanceFromBorder(point: Has2DLocation, limits: Dimensions): Double = {
    val lowX = point.x - limits.minX
    val lowY = point.y - limits.minY
    val highX = limits.maxX - point.x
    val highY = limits.maxY - point.y
    val minLow = if (lowX < lowY) lowX else lowY
    val minHigh = if (highX < highY) highX else highY
    if (minLow < minHigh) minLow else minHigh
  }
}
