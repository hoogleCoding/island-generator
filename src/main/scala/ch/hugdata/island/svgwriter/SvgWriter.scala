package ch.hugdata.island.svgwriter

import java.io.{File, PrintWriter}

/**
  * Writes svg files
  */
class SvgWriter()(implicit private val dimensions: Dimensions) {

  private def renderSvg(title: Option[String],
                        description: Option[String],
                        body: Option[String]): String =
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
       |<svg xmlns="http://www.w3.org/2000/svg"
       |xmlns:xlink="http://www.w3.org/1999/xlink"
       |version="1.1" baseProfile="full"
       |width="400mm" height="400mm"
       |viewBox="${dimensions.minX} ${dimensions.minY} ${dimensions.maxX} ${dimensions.maxY}">
       |<title>${title.getOrElse("")}</title>
       |<desc>${description.getOrElse("")}</desc>
       |${body.getOrElse("")}
       |</svg>
       |""".stripMargin

  def writeToFile(filename: String,
                  data: String,
                  title: Option[String] = None,
                  description: Option[String] = None): Unit = {
    val writer = new PrintWriter(new File(filename))
    val svg = renderSvg(title, description, Some(data))
    writer.write(svg)
    writer.flush()
    writer.close()
  }
}
