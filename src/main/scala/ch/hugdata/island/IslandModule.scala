package ch.hugdata.island

import ch.hugdata.island.generator.{CoordinateTransformer, Generator, IdentityCoordinateTransformer}
import ch.hugdata.island.graph.VoronoiCalculator
import ch.hugdata.island.svgwriter.gradient.{ColorFixPoint, ColorGradient}
import ch.hugdata.island.svgwriter.{Color, Dimensions, SvgWriter}

import scala.util.Try

/**
  * Configuration for dependency injection
  */
trait IslandModule {

  import com.softwaremill.macwire._

  val seed: Int

  implicit val dimensions: Dimensions

  lazy val generator = new Generator(seed)

  lazy val svgWriter: SvgWriter = wire[SvgWriter]

  lazy val coordinateTransformer: CoordinateTransformer = wire[IdentityCoordinateTransformer]

  lazy val voronoiCalculator: VoronoiCalculator = wire[VoronoiCalculator]

  val gradient: Try[ColorGradient] = Try {
    Seq(ColorFixPoint(0, Color(0, 156, 189)),
      ColorFixPoint(0.1, Color(215, 147, 48)),
      ColorFixPoint(0.2, Color(0, 200, 20)),
      ColorFixPoint(1.0, Color(229, 225, 230)))
      .map(_.get)
  }.map(ColorGradient)

}
