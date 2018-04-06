package ch.hugdata.island.svgwriter

/**
  * Type for color representation
  */
case class Color(red: Int, green: Int, blue: Int, alpha: Int = 255) {

  def toSVG = s"""rgba($red,$green,$blue,${alpha / 255})"""

}

object Color {
  val black = Color(0, 0, 0)
  val white = Color(255, 255, 255)
}
