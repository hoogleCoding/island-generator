package ch.hugdata.island.svgwriter.gradient

import ch.hugdata.island.svgwriter.Color

import scala.util.Try

/**
  * Type for the generation of color gradients between fixed values
  */
case class ColorGradient(fixPoints: Seq[ColorFixPoint])
  extends Gradient {

  private val gradientFixPoints = fixPoints.sortBy(_.value)
  private val minValue = gradientFixPoints.head.value
  private val maxValue = gradientFixPoints.last.value

  override def getColor(value: Double): Option[Color] = {
    if (value < minValue || value > maxValue) {
      None
    } else {
      val lowValue = Try {
        gradientFixPoints.filter(_.value <= value).last
      }.map(Some(_)).getOrElse(None)
      val highValue = gradientFixPoints.find(_.value >= value)
      for {
        low <- lowValue
        high <- highValue
      } yield colorBetween(low, high, value)

    }
  }

  private def colorBetween(lowValue: ColorFixPoint, highValue: ColorFixPoint, currentValue: Double): Color = {

    def colorFraction(lowColor: Color, highColor: Color, fraction: Double) = {
      val red = valuePercentage(lowColor.red, highColor.red, fraction)
      val green = valuePercentage(lowColor.green, highColor.green, fraction)
      val blue = valuePercentage(lowColor.blue, highColor.blue, fraction)
      val alpha = valuePercentage(lowColor.alpha, highColor.alpha, fraction)
      Color(red, green, blue, alpha)
    }

    def valuePercentage(lowValue: Double, highValue: Double, fraction: Double): Int = {
      (lowValue + (highValue - lowValue) * fraction).toInt
    }

    if (lowValue.equals(highValue)) {
      lowValue.color
    } else {
      val delta = highValue.value - lowValue.value
      val normalized = currentValue - lowValue.value
      val relativeValue = normalized / delta
      colorFraction(lowValue.color, highValue.color, relativeValue)
    }
  }

}


