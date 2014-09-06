
package me.currencies.model

/**
 * <pre> Currency Definition <pre/>
 * @param name the name of the currency
 * @param unit the unit of the currency
 * @param currencyCode the code of the currency
 * @param country the country of the currency
 * @param rate the rate of the currency
 * @param change the change of the currency
 */
class Currency(name: String, unit: Int, currencyCode: String, country: String, rate: Double, change: Double) {

  private val m_name = name
  private val m_unit = unit
  private val m_currencyCode = currencyCode
  private val m_country = country
  private val m_rate = rate
  private val m_change = change

  def getName(): String = {
    m_name
  }

  def getUnit(): Int = {
    m_unit
  }

  def getCurrencyCode(): String = {
    m_currencyCode
  }

  def getCountry(): String = {
    m_country
  }

  def getRate(): Double = {
    m_rate
  }

  def getChange: Double = {
    m_change
  }

}

