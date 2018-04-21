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

    val seedPoints: Seq[Point2D] = generator.generatePoints(dimensions, 2000)

    val voronoiGraph: VoronoiGraph = voronoiCalculator.iterateVoronoi(seedPoints, 4)

    val edges: EdgeGraph2D = edgeGenerator.generateEdges(voronoiGraph)
    val edgeGraph3D = coordinateTransformer.generate(edges)

    println(s"Total length of all edges: ${edges.edges.map(_.length).sum}")

    val group = mapDrawer.drawMap(edgeGraph3D)

    svgWriter.writeToFile("/tmp/test.svg", group.toSVG().getOrElse(""))
  }

  def getUpdatedCenters(polygons: Seq[Polygon2D]): Seq[Point2D] = polygons.map(_.center)

}
