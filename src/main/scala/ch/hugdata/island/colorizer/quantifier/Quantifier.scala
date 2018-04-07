package ch.hugdata.island.colorizer.quantifier

/**
  * Converts any properties of an item into a double value.
  */
trait Quantifier[Type] {

  /**
    * Takes any property of an item and returns a value between 0 and 1
    *
    * @param item The item to quantify
    * @return A double value between 0.0 and 1.0
    */
  def quantify(item: Type): Double

}
