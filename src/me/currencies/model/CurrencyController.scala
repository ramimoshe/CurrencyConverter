package me.currencies.model

import scala.collection.Map

/**
 * Currency Controller - responsible to negotiate between UI and Model
 */
trait CurrencyController {
  // return currency map with full details
  def getCurrencies(): Map[String, Currency]

  // return list of all currency names
  def getCurrenciesNames(): Array[String]

  // return the last update date of the model data
  def getLastUpdate() : String

  // convert currency with types from getCurrenciesNames method
  def convert(amount: Double, from: String, to: String): Double
}

