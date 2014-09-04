package me.currencies.model

import scala.collection.Map

/**
 * Created by user on 02/09/2014.
 */
trait CurrencyController {
  def getCurrencies(): Map[String, Currency]

  def getCurrenciesNames(): Array[String]

  def getLastUpdate() : String

  def convert(amount: Double, from: String, to: String): Double

}

