package ch.hugdata.island.graph

/**
  * Trait for types which can be represented as a 2D polygon.
  */
trait Is2DPolygon {
  def getPoints: Seq[Has2DLocation]

}
