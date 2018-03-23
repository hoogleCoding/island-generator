package ch.hugdata.island.svgwriter

import scala.util.Try

/**
  * Represents a group of [[SvgElement]]s
  */
class Group(private val elements: Seq[SvgElement],
            private val properties: Seq[Property] = Seq.empty)
  extends SvgElement {

  private def renderSvg(elements: Seq[String]) = {
    s"""<g ${properties.map(_.toSvg).mkString(" ")} >
       |${elements.mkString("\n")}
       |</g>""".stripMargin
  }

  def toSVG()(implicit limits: Dimensions): Try[String] = Try {
    elements.map(_.toSVG().get)
  }.map(renderSvg)

}
