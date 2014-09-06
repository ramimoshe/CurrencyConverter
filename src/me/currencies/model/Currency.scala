
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

  /**
   * get Name
   * @return currency name
   */
  def getName: String = {
    m_name
  }

  /**
   * get unit
   * @return currency unit
   */
  def getUnit: Int = {
    m_unit
  }

  /**
   * get currency code
   * @return - currency code
   */
  def getCurrencyCode: String = {
    m_currencyCode
  }

  /**
   * get country
   * @return country of the currency
   */
  def getCountry: String = {
    m_country
  }


  /**
   * get rate
   * @return rate - to NIS
   */
  def getRate: Double = {
    m_rate
  }

  /**
   * returns the change rate
   * @return change rate
   */
  def getChange: Double = {
    m_change
  }

}

