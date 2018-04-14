package ch.hugdata.island.svgwriter

import ch.hugdata.island.graph.twodee.{Point2D, Polygon2D}
import org.scalatest.FlatSpec

/**
  * Specification for the [[Polygon]] type
  */
class PolygonSpec
  extends FlatSpec {
  val polygon2d = Polygon2D(
    Seq(
      Point2D(432.85, 160.733),
      Point2D(454.997, 168.84),
      Point2D(456.96, 196.593),
      Point2D(453.602, 201.513),
      Point2D(421.765, 191.113)),
    Point2D(404.8070983886719, 176.2927474975586))
  val properties = Seq(Property.stroke("black"), Property.strokeWith(2), Property.fill("none"))

  "A Polygon" should "serialize to an svg element" in {
    implicit val limits: Dimensions = Dimensions(0, 1000, 0, 1000)
    val polygon = Polygon(polygon2d, properties)
    val svgResult = polygon.toSVG()
    assert(svgResult.isSuccess === true)
    assert(svgResult.get === "<polygon points=\"432.85,160.733 454.997,168.84 456.96,196.593 453.602,201.513 421.765,191.113\" stroke=\"black\" stroke-with=\"2\" fill=\"none\" />")
  }

  it should "not serialize if the polygon is outside of the limits." in {
    implicit val limits: Dimensions = Dimensions(0, 400, 0, 400)
    val polygon = Polygon(polygon2d, properties)
    val svgResult = polygon.toSVG()
    assert(svgResult.isSuccess === false)
  }

}
