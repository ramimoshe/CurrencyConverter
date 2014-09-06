package me.currencies.model

import scala.collection.Map

/**
 * Currency Controller - responsible to negotiate between UI and Model
 */
trait CurrencyController {
  /**
   * return currency map with full details
   * @return
   */
  def getCurrencies: Map[String, Currency]

  /**
   * return list of all currency names
   * @return
   */
  def getCurrenciesNames: Array[String]


  /**
   * return the last update date of the model data
   * @return
   */
  def getLastUpdate: String


  /**
   * convert currency with types from getCurrenciesNames method
   * @param amount - amount of the currency
   * @param from - from currencyCode
   * @param to - to Currency Code
   * @return - amount
   */
  def convert(amount: Double, from: String, to: String): Double
}

