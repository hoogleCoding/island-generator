package ch.hugdata.island.generator

import ch.hugdata.island.svgwriter.Dimensions
import org.scalatest.FlatSpec

/**
  * Specification for the [[Generator]] type.
  */
class GeneratorSpec extends FlatSpec {

  "A generator" should "generate points within the limits" in {
    val seed = 293741
    val numberOfPoints = 10000
    val limits = Dimensions(0, 100, 0, 100)
    val generator: Generator = Generator(seed)
    val sample = Range(0,numberOfPoints)
      .map(_ => generator.generatePoint(limits))
      .forall(point => limits.withinLimits(point.x,point.y))
    assert(sample === true)
  }

  it should "generator the specified number of distinct points in batch" in {
    val seed = 324348
    val limits = Dimensions(0, 100, 0, 1000)
    val generator: Generator = Generator(seed)
    val numOfPoints = 122
    val points = generator.generatePoints(limits, numOfPoints)
    assert(points.distinct.length === numOfPoints)
  }

  it should "generate the same points when initialized with the same seed" in {
    val seed = 98384
    val limits = Dimensions(0, 100, 0, 100)
    val generator0: Generator = Generator(seed)
    val generator1: Generator = Generator(seed)

    val numOfPoints = 1337
    val generator0Points = generator0.generatePoints(limits, numOfPoints)
    val generator1Points = generator1.generatePoints(limits, numOfPoints)
    val allEqual = generator0Points
      .zip(generator1Points)
      .forall(tuple => tuple._1.equals(tuple._2))

    assert(allEqual === true)
  }

}
