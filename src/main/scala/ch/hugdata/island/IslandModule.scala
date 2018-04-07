package ch.hugdata.island

import ch.hugdata.island.colorizer.gradient.{ColorFixPoint, ColorGradient}
import ch.hugdata.island.colorizer.quantifier.{PointElevationQuantifier, Quantifier}
import ch.hugdata.island.colorizer.{Colorizer, ConcreteColorizer}
import ch.hugdata.island.generator.{CoordinateTransformer, Generator, HalfDomeCoordinateTransformer}
import ch.hugdata.island.graph.{Point3D, VoronoiCalculator}
import ch.hugdata.island.svgwriter.{Color, Dimensions, PropertyAssembler, SvgWriter}
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

  lazy val point3DQuantifier: Quantifier[Point3D] = wire[PointElevationQuantifier]

  lazy val colorizer: Colorizer[Point3D] = wire[ConcreteColorizer[Point3D]]

  lazy val point3DPropertyAssembler: PropertyAssembler[Point3D] = wire[PropertyAssembler[Point3D]]

  val gradient: ColorGradient = Try {
    Seq(ColorFixPoint(0, Color(0, 156, 189)),
      ColorFixPoint(0.1, Color(215, 147, 48)),
      ColorFixPoint(0.2, Color(0, 200, 20)),
      ColorFixPoint(1.0, Color(229, 225, 230)))
      .map(_.get)
  }.map(ColorGradient)
    .get

}

trait MaxAltitude
