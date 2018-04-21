package ch.hugdata.island

import ch.hugdata.island.colorizer.gradient.{ColorFixPoint, ColorGradient}
import ch.hugdata.island.colorizer.quantifier.{PointElevationQuantifier, PolygonElevationQuantifier, Quantifier}
import ch.hugdata.island.colorizer.{Colorizer, ConcreteColorizer}
import ch.hugdata.island.generator.{CoordinateTransformer, Generator, HalfDomeCoordinateTransformer}
import ch.hugdata.island.graph.threedee.{Point3D, Polygon3D}
import ch.hugdata.island.graph.triangulation.{MostAcuteCornerTriangulation, Triangulation}
import ch.hugdata.island.graph.{EdgeGenerator, VoronoiCalculator}
import ch.hugdata.island.map.MapDrawer
import ch.hugdata.island.svgwriter._
import com.softwaremill.macwire._
import com.softwaremill.tagging._

import scala.util.Try

/**
  * Configuration for dependency injection
  */
trait IslandModule {


  val seed: Int

  implicit val dimensions: Dimensions

  val maximumAltitude: Double @@ MaxAltitude = 1000.0.taggedWith[MaxAltitude]

  lazy val generator = new Generator(seed)

  lazy val svgWriter: SvgWriter = wire[SvgWriter]

  lazy val coordinateTransformer: CoordinateTransformer = wire[HalfDomeCoordinateTransformer]

  lazy val voronoiCalculator: VoronoiCalculator = wire[VoronoiCalculator]

  lazy val edgeGenerator: EdgeGenerator = wire[EdgeGenerator]

  lazy val triangulation: Triangulation = wire[MostAcuteCornerTriangulation]

  lazy val point3DQuantifier: Quantifier[Point3D] = wire[PointElevationQuantifier]

  lazy val point3DColorizer: Colorizer[Point3D] = wire[ConcreteColorizer[Point3D]]

  lazy val point3DPropertyAssembler: PropertyAssembler[Point3D] = wire[PropertyAssembler[Point3D]]

  lazy val polygon3DQuantifier: Quantifier[Polygon3D] = wire[PolygonElevationQuantifier]

  lazy val polygon3DColorizer: Colorizer[Polygon3D] = wire[ConcreteColorizer[Polygon3D]]

  lazy val polygon3DPropertyAssembler: PropertyAssembler[Polygon3D] = wire[PropertyAssembler[Polygon3D]]

  lazy val mapDrawer: MapDrawer = wire[MapDrawer]

  val gradient: ColorGradient = Try {
    Seq(ColorFixPoint(0, Color(1, 38, 119)),
      ColorFixPoint(0.09, Color(0, 91, 197)),
      ColorFixPoint(0.1, Color(215, 147, 48)),
      ColorFixPoint(0.2, Color(75, 110, 40)),
      ColorFixPoint(0.8, Color(66, 165, 80)),
      ColorFixPoint(1.0, Color(142, 126, 100)))
      .map(_.get)
  }.map(ColorGradient)
    .get

  val nodeProperties: Seq[Property] @@ NodeProperties = Seq(Property.size(3)).taggedWith[NodeProperties]

  val polygonProperties: Seq[Property] @@ PolygonProperties = Seq(Property.strokeWith(0)).taggedWith[PolygonProperties]


}

trait MaxAltitude

trait NodeProperties

trait PolygonProperties
